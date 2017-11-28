package com.logistimo.social.action;

import com.logistimo.social.core.model.GetLikeResponseModel;
import com.logistimo.social.core.model.LikeModel;
import com.logistimo.social.core.model.PageParam;
import com.logistimo.social.repository.LikeRepositoryCustom;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 21/11/17.
 */
@Component
public class GetLikeForActivityAction {


  @Resource
  LikeRepositoryCustom customRepository;


  public GetLikeResponseModel invoke (String objectId, String objectType, String contextId, int offset, int size) {

    GetLikeResponseModel response = new GetLikeResponseModel();
    List<LikeModel> likes = null;
    int total = 0;
    if (StringUtils.isEmpty(contextId)) {
      likes = customRepository
          .getLikesByObj(objectId, objectType, new PageParam(offset, size));
      total = customRepository.countLikesByObj(objectId, objectType).intValue();
    } else {
      likes = customRepository
          .getLikesByObjAndContxt(objectId, objectType, contextId, new PageParam(offset, size));
      total = customRepository.countLikesByObjAndContxt(objectId, objectType, contextId).intValue();
    }
    response.setLikes(likes);
    response.offset = offset;
    response.size = likes.size();
    response.total = total;
    return response;
  }

}
