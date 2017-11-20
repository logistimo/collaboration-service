package com.logistimo.social.action;

import com.logistimo.social.core.model.ActivityLikeResponseModel;
import com.logistimo.social.core.model.SocialLikeModel;
import com.logistimo.social.entity.Like;
import com.logistimo.social.repository.LikeRepository;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Component
public class GetLikeForActivityAction {


  @Resource
  LikeRepository lkRepository;


  public ActivityLikeResponseModel invoke (String activityid, int offset, int size) {

    ActivityLikeResponseModel response = new ActivityLikeResponseModel();
    List<Like> likes = lkRepository.findBySactvtyid(activityid,new PageRequest(offset,size));
    int total = lkRepository.countBySactvtyid(activityid).intValue();
    if (likes.size() >0) {
      ModelMapper mapper = new ModelMapper();
      response.likes = likes.stream().map(like ->mapper.map(like, SocialLikeModel.class)).collect(
          Collectors.toList());
      response.page = offset;
      response.size = likes.size();
      response.total = total;
    }
    return response;
  }

}
