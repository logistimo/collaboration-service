package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.LikerResponseModel;
import com.logistimo.collaboration.core.models.SocialLikerModel;
import com.logistimo.collaboration.entities.Like;
import com.logistimo.collaboration.repositories.LikeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Component
public class GetLikerForActivityAction {


  LikeRepository lkRepository;


  public LikerResponseModel invoke(String objectId, String objectType, String contextId, int offset,
                                   int size) {

    LikerResponseModel response = new LikerResponseModel();
    int total = getCount(objectId, objectType, contextId).intValue();
    response.total = total;
    if (total == 0) {
      return response;
    }
    List<SocialLikerModel> likers = getLikesrDetail(objectId, objectType, contextId, offset, size);
    response.setLikers(likers);
    response.offset = offset;
    response.size = likers.size();
    return response;
  }

  private Long getCount(String objectId, String objectType, String contextId) {
    long total = 0;
    if (StringUtils.isEmpty(contextId)) {
      total = lkRepository.countLikersByObj(objectId, objectType);
    } else {
      total = lkRepository.countLikersByObjAndContxt(objectId, objectType, contextId);
    }
    return total;
  }

  private List<SocialLikerModel> getLikesrDetail(String objectId, String objectType,
                                                 String contextId, int offset, int size) {
    List<Like> likes = null;
    if (StringUtils.isEmpty(contextId)) {
      likes = lkRepository
          .findLikersByObj(objectId, objectType,
              new PageRequest(offset, size, Sort.Direction.DESC, "cron"));
    } else {
      likes = lkRepository
          .findLikersByObjAndContxt(objectId, objectType, contextId,
              new PageRequest(offset, size, Sort.Direction.DESC, "cron"));
    }
    if (likes.size() == 0) {
      return Collections.emptyList();
    }
    ModelMapper mapper = new ModelMapper();
    return likes.stream().map(like -> mapper.map(like, SocialLikerModel.class))
        .collect(Collectors.toList());
  }

  @Autowired
  public void setLkRepository(LikeRepository lkRepository) {
    this.lkRepository = lkRepository;
  }
}
