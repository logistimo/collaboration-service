package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.LikerResponseModel;
import com.logistimo.collaboration.core.models.SocialLikerModel;
import com.logistimo.collaboration.entities.Like;
import com.logistimo.collaboration.repositories.LikeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Component
public class GetLikerForActivityAction {


  @Resource
  LikeRepository lkRepository;


  public LikerResponseModel invoke (String objectId,String objectType, String contextId, int offset, int size) {

    LikerResponseModel response = new LikerResponseModel();
    List<Like> likes = null;
    int total = 0;
    if (StringUtils.isEmpty(contextId)) {
      likes = lkRepository
          .findLikersByObj(objectId, objectType, new PageRequest(offset, size, Sort.Direction.DESC,"cron"));
      total = lkRepository.countLikersByObj(objectId, objectType).intValue();
    } else {
      likes = lkRepository
          .findLikersByObjAndContxt(objectId, objectType, contextId, new PageRequest(offset, size,Sort.Direction.DESC,"cron"));
      total = lkRepository.countLikersByObjAndContxt(objectId, objectType, contextId).intValue();
    }
    if (likes.size() >0) {
      ModelMapper mapper = new ModelMapper();
      response.setLikers(likes.stream().map(like ->mapper.map(like, SocialLikerModel.class)).collect(
          Collectors.toList()));
      response.offset = offset;
      response.size = likes.size();
      response.total = total;
    }
    return response;
  }

}
