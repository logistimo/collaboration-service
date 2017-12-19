package com.logistimo.collaboration.service;

import com.logistimo.collaboration.core.constants.SocialType;
import com.logistimo.collaboration.core.models.RegisterLikeRequestModel;
import com.logistimo.collaboration.core.models.RegisterLikeResponseModel;
import com.logistimo.collaboration.entities.Like;
import com.logistimo.collaboration.entities.SocialActivity;
import com.logistimo.collaboration.entities.SocialContext;
import com.logistimo.collaboration.repositories.LikeRepository;
import com.logistimo.collaboration.repositories.SocialActivityRepository;
import com.logistimo.collaboration.repositories.SocialContextRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by kumargaurav on 08/12/17.
 */
@Service
public class LIkePersistenceService {

  SocialContextRepository scRepository;

  SocialActivityRepository saRepository;

  LikeRepository lkRepository;

  public RegisterLikeResponseModel persist(RegisterLikeRequestModel model) {
    String type = SocialType.getValue(model.getType());
    SocialContext context = createContext(model);
    SocialActivity activity = createActivity(context.getId(),model.getUser(),type);
    if (type.equals(SocialType.LIKE.name())) {
      createLike(activity, model.getUser(), model.getSrc());
    }
    return constructResponse(model,activity);
  }

  private SocialContext createContext(RegisterLikeRequestModel model) {
    SocialContext context = null;
    context =   scRepository.findContextByObjectAndContxt(model.getObjectId(), model.getObjectType(),
            model.getContextId(), model.getContextType());
    if (context == null) {
      context = new SocialContext();
      context.setObjid(model.getObjectId());
      context.setObjty(model.getObjectType());
      context.setContextid(model.getContextId());
      context.setContextty(model.getContextType());
      context.setContextattr(model.getContextAttribute());
      context.setCrby(model.getUser());
      context.setCron(new Date());
      scRepository.save(context);
    }
    return context;
  }

  private SocialActivity createActivity(String contextId, String user, String type) {
    SocialActivity activity = null;
    activity = saRepository.findActivityByContxtAndtype(contextId, type);
    if (activity == null) {
      activity = new SocialActivity();
      activity.setScontxtid(contextId);
      activity.setActvtyty(type);
      activity.setCrby(user);
      activity.setCron(new Date());
      saRepository.save(activity);
    }
    return activity;
  }

  private void createLike(SocialActivity activity, String user, String src) {
    Like like = new Like();
    like.setSactvtyid(activity.getId());
    like.setScontxtid(activity.getScontxtid());
    like.setLiker(user);
    like.setSrc(src);
    like.setCron(new Date());
    lkRepository.save(like);
  }

  private RegisterLikeResponseModel constructResponse(RegisterLikeRequestModel model,
                                                      SocialActivity activity) {
    RegisterLikeResponseModel
        response =
        new RegisterLikeResponseModel(model.getObjectId(), model.getObjectType(),
            model.getContextId(),
            model.getContextType(), activity.getId(), model.getContextAttribute());
    //get likes
    response.setCounts(lkRepository.countBySactvtyid(activity.getId()).intValue());
    return response;
  }

  public boolean likeExists(RegisterLikeRequestModel model) {
    boolean result;
    if (StringUtils.isEmpty(model.getContextId())) {
      result =
          lkRepository
              .countLikersByObjAndUser(model.getObjectId(), model.getObjectType(), model.getUser())
              == 0 ? false : true;
    } else {
      result =
          lkRepository.countLikersByObjAndContxtAndUser(model.getObjectId(), model.getObjectType(),
              model.getContextId(), model.getUser()) == 0 ? false : true;
    }
    return result;
  }

  @Autowired
  public void setScRepository(
      SocialContextRepository scRepository) {
    this.scRepository = scRepository;
  }

  @Autowired
  public void setSaRepository(
      SocialActivityRepository saRepository) {
    this.saRepository = saRepository;
  }

  @Autowired
  public void setLkRepository(LikeRepository lkRepository) {
    this.lkRepository = lkRepository;
  }

}
