package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.GetLikeResponseModel;
import com.logistimo.collaboration.core.models.LikeModel;
import com.logistimo.collaboration.core.models.PageParam;
import com.logistimo.collaboration.repositories.LikeRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by kumargaurav on 21/11/17.
 */
@Component
public class GetLikeForActivityAction {


  LikeRepositoryCustom customRepository;


  public GetLikeResponseModel invoke (String objectId, String objectType, String contextId, boolean count, int offset, int size) {

    GetLikeResponseModel response = new GetLikeResponseModel();
    int total = getLikeCount(objectId,objectType,contextId).intValue();
    response.total = total;
    if (count) {
      return response;
    }
    List<LikeModel> likes = getLikes(objectId,objectType,contextId,offset, size);
    response.setLikes(likes);
    response.setSize(likes.size());
    response.setOffset(offset);
    return response;
  }


  private Long getLikeCount (String objectId, String objectType, String contextId) {
    long total = 0;
    if (StringUtils.isEmpty(contextId)) {
      total = customRepository.countLikesByObj(objectId, objectType);
    } else {
      total = customRepository.countLikesByObjAndContxt(objectId, objectType, contextId);
    }
    return total;
  }

  private List<LikeModel> getLikes (String objectId, String objectType, String contextId, int offset, int size) {
    List<LikeModel> likes = null;
    if (StringUtils.isEmpty(contextId)) {
      likes = customRepository
          .getLikesByObj(objectId, objectType, new PageParam(offset, size));
    } else {
      likes = customRepository
          .getLikesByObjAndContxt(objectId, objectType, contextId, new PageParam(offset, size));
    }
    return likes;
  }

  @Autowired
  public void setCustomRepository(
      LikeRepositoryCustom customRepository) {
    this.customRepository = customRepository;
  }
}
