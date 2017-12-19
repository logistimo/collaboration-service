package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.LikeCountContainer;
import com.logistimo.collaboration.core.models.LikeCountModel;
import com.logistimo.collaboration.repositories.LikeRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by kumargaurav on 22/11/17.
 */
@Component
public class GetCountForManyAction {

  LikeRepositoryCustom customRepository;

  public LikeCountContainer invoke (LikeCountContainer request) {
    LikeCountContainer res = getResponse(customRepository.getLikesForMany(request.getLikes(),request.getUser()));
    res.setUser(request.getUser());
    return res;
  }

  private LikeCountContainer getResponse (List<LikeCountModel> likes) {
    LikeCountContainer c = new LikeCountContainer();
    c.setLikes(likes);
    return c;
  }

  @Autowired
  public void setCustomRepository(
      LikeRepositoryCustom customRepository) {
    this.customRepository = customRepository;
  }
}
