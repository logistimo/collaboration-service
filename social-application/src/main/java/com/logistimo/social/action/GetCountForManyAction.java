package com.logistimo.social.action;

import com.logistimo.social.core.model.LikeCountContainer;
import com.logistimo.social.core.model.LikeCountModel;
import com.logistimo.social.repository.LikeRepositoryCustom;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 22/11/17.
 */
@Component
public class GetCountForManyAction {

  @Resource
  LikeRepositoryCustom customRepository;

  public LikeCountContainer invoke (LikeCountContainer request) {

    LikeCountContainer c = new LikeCountContainer();
    c.setLikes(customRepository.getLikesForMany(request.getLikes(),request.getUser()));
    c.setUser(request.getUser());
    return c;
  }

}
