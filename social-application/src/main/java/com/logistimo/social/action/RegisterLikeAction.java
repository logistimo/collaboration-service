package com.logistimo.social.action;

import com.logistimo.social.core.constants.SocialType;
import com.logistimo.social.core.event.Event;
import com.logistimo.social.core.event.LikeRegisteredEvent;
import com.logistimo.social.core.model.LikeRequestModel;
import com.logistimo.social.core.model.LikeResponseModel;
import com.logistimo.social.core.model.SocialLikeModel;
import com.logistimo.social.entity.Like;
import com.logistimo.social.entity.SocialActivity;
import com.logistimo.social.entity.SocialContext;
import com.logistimo.social.repository.LikeRepository;
import com.logistimo.social.repository.SocialActivityRepository;
import com.logistimo.social.repository.SocialContextRepository;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

  @Produce(uri = "jms:event")
  ProducerTemplate producer;


  @Transactional(transactionManager = "scTransactionManager",propagation = Propagation.REQUIRES_NEW)
  public LikeResponseModel invoke (LikeRequestModel likeRequestModel) {
    //validate request data
    validate(likeRequestModel);
    LikeResponseModel response =  persist(likeRequestModel);
    //raise events
    producer.sendBody(constructEvent(response, likeRequestModel.getUser()));
    return response;
  }

  private void validate(LikeRequestModel model) {
    //validating the request data
    Set<ConstraintViolation<LikeRequestModel>> violations = validator.validate(model);
    if (!violations.isEmpty() && violations.size() > 0) {
      StringBuilder errBuilder = new StringBuilder();
      violations.forEach(violation -> errBuilder.append(violation.getMessage()));
      log.error("Invalid request with data {} and error {}", model, errBuilder.toString());
      throw new ValidationException(errBuilder.toString());
    }
  }

  private LikeResponseModel persist(LikeRequestModel model) {

    String type = SocialType.getValue(model.getType());
    SocialActivity activity = null;
    //check whether social context exists or not
    SocialContext scontxt = scRepository.findContextByObjectAndContxt(model.getObjectid(),model.getObjectty(),model.getContextid(),model.getContextty());
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

  private SocialContext createContext (LikeRequestModel model) {
    SocialContext scontxt = new SocialContext();
    scontxt.setObjid(model.getObjectid());
    scontxt.setObjty(model.getObjectty());
    scontxt.setContextid(model.getContextid());
    scontxt.setContextty(model.getContextty());
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

  private LikeResponseModel constructResponse(LikeRequestModel model, SocialActivity activity) {
    LikeResponseModel response = new LikeResponseModel(model.getObjectid(),model.getObjectty(),model.getContextid(),
        model.getContextty(),activity.getId(),model.getContxtattr());
    //get likes
    response.setLikecount(lkRepository.countBySactvtyid(activity.getId()).intValue());
    return response;
  }

  private Event constructEvent(LikeResponseModel response,String user) {
    LikeRegisteredEvent event = new LikeRegisteredEvent();
    event.setObjectid(response.getObjectid());
    event.setObjectty(response.getObjectty());
    event.setContextid(response.getContextid());
    event.setContextty(response.getContextty());
    event.setContxtattr(response.getContxtattr());
    event.setUser(user);
    return event;
  }


}
