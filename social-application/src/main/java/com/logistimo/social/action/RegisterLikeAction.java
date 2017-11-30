package com.logistimo.social.action;

import com.google.gson.GsonBuilder;

import com.logistimo.social.core.constants.SocialType;
import com.logistimo.social.core.event.Event;
import com.logistimo.social.core.event.LikeRegisteredEvent;
import com.logistimo.social.core.model.RegisterLikeRequestModel;
import com.logistimo.social.core.model.RegisterLikeResponseModel;
import com.logistimo.social.entity.Like;
import com.logistimo.social.entity.SocialActivity;
import com.logistimo.social.entity.SocialContext;
import com.logistimo.social.repository.LikeRepository;
import com.logistimo.social.repository.SocialActivityRepository;
import com.logistimo.social.repository.SocialContextRepository;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Component
public class RegisterLikeAction {

  private static final Logger log = LoggerFactory.getLogger(RegisterLikeAction.class);

  @Resource
  Validator validator;

  @Resource
  SocialContextRepository scRepository;

  @Resource
  SocialActivityRepository saRepository;

  @Resource
  LikeRepository lkRepository;

  @Produce(uri = "jms:collab-events")
  ProducerTemplate producer;


  @Transactional(transactionManager = "scTransactionManager",propagation = Propagation.REQUIRES_NEW)
  public RegisterLikeResponseModel invoke (RegisterLikeRequestModel likeRequestModel) {
    //validate request data
    validate(likeRequestModel);
    RegisterLikeResponseModel response =  persist(likeRequestModel);
    //raise events
    Event e = constructEvent(response, likeRequestModel.getUser());
    log.info("sending regitered event {}",e);
    producer.sendBody(getData(e));
    return response;
  }

  private void validate(RegisterLikeRequestModel model) {
    //validating the request data
    Set<ConstraintViolation<RegisterLikeRequestModel>> violations = validator.validate(model);
    if (!violations.isEmpty() && violations.size() > 0) {
      StringBuilder errBuilder = new StringBuilder();
      violations.forEach(violation -> errBuilder.append(violation.getMessage()));
      log.error("Invalid request with data {} and error {}", model, errBuilder.toString());
      throw new ValidationException(errBuilder.toString());
    }
  }

  private RegisterLikeResponseModel persist(RegisterLikeRequestModel model) {

    String type = SocialType.getValue(model.getType());
    SocialActivity activity = null;
    //check whether social context exists or not
    SocialContext scontxt = scRepository.findContextByObjectAndContxt(model.getObjectId(),model.getObjectType(),model.getContextId(),model.getContextType());
    if (scontxt == null) {
      scontxt = createContext(model);
    } else {
      //check whether activity exists
      activity = saRepository.findActivityByContxtAndtype(scontxt.getId(),type);
    }
    if (activity == null) {
      activity = createActivity(scontxt.getId(),model.getUser(),type);
    }
    if (type.equals(SocialType.LIKE.name())) {
      createLike(activity, model.getUser(), model.getSrc());
    }
    return constructResponse(model,activity);
  }

  private SocialContext createContext (RegisterLikeRequestModel model) {
    SocialContext scontxt = new SocialContext();
    scontxt.setObjid(model.getObjectId());
    scontxt.setObjty(model.getObjectType());
    scontxt.setContextid(model.getContextId());
    scontxt.setContextty(model.getContextType());
    scontxt.setContextattr(model.getContextAttribute());
    scontxt.setCrby(model.getUser());
    scontxt.setCron(new Date());
    return scRepository.save(scontxt);
  }

  private SocialActivity createActivity (String scontxtid, String user, String type) {
    SocialActivity activity = new SocialActivity();
    activity.setScontxtid(scontxtid);
    activity.setActvtyty(type);
    activity.setCrby(user);
    activity.setCron(new Date());
    return saRepository.save(activity);
  }

  private Like createLike (SocialActivity activity, String user, String src) {
    Like like = new Like();
    like.setSactvtyid(activity.getId());
    like.setScontxtid(activity.getScontxtid());
    like.setLiker(user);
    like.setSrc(src);
    like.setCron(new Date());
    return lkRepository.save(like);
  }

  private RegisterLikeResponseModel constructResponse(RegisterLikeRequestModel model, SocialActivity activity) {
    RegisterLikeResponseModel
        response = new RegisterLikeResponseModel(model.getObjectId(),model.getObjectType(),model.getContextId(),
        model.getContextType(),activity.getId(),model.getContextAttribute());
    //get likes
    response.setLikecount(lkRepository.countBySactvtyid(activity.getId()).intValue());
    return response;
  }

  private Event constructEvent(RegisterLikeResponseModel response, String user) {
    LikeRegisteredEvent event = new LikeRegisteredEvent();
    event.setObjectid(response.getObjectId());
    event.setObjectty(response.getObjectType());
    event.setContextid(response.getContextId());
    event.setContextty(response.getContextType());
    event.setContxtattr(response.getContextAttribute());
    event.setUser(user);
    return event;
  }

  private String getData (Event event) {
    return new GsonBuilder().create().toJson(event);
  }

}
