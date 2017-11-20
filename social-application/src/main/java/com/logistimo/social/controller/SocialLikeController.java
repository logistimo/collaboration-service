package com.logistimo.social.controller;

import com.logistimo.social.action.GetLikeForActivityAction;
import com.logistimo.social.action.RegisterLikeAction;
import com.logistimo.social.core.model.ActivityLikeResponseModel;
import com.logistimo.social.core.model.LikeRequestModel;
import com.logistimo.social.core.model.LikeResponseModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 08/11/17.
 */
@RestController
@RequestMapping(path = "/like")
public class SocialLikeController {

  private static final Logger logger = LoggerFactory.getLogger(SocialLikeController.class);

  @Resource
  RegisterLikeAction  createAction;

  @Resource
  GetLikeForActivityAction getAction;

  @RequestMapping(path = "", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody LikeResponseModel registerLike (@RequestBody LikeRequestModel likeRequestModel) {

    return createAction.invoke(likeRequestModel);
  }

  @RequestMapping(path = "/{obj_ty}/{obj_id}/{contxt_id}/{contxt_ty}", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  ActivityLikeResponseModel getLikes (@PathVariable(name = "obj_id") String objid,
                                      @PathVariable(name = "obj_ty") String objty,
                                      @PathVariable(name = "contxt_id") String contxtid,
                                      @PathVariable(name = "contxt_ty") String contxtty,
                                      @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                      @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return null;
  }

  @RequestMapping(path = "", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  ActivityLikeResponseModel getLikesForActivity (@RequestParam(name = "activity") String activityid,
                                                 @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                                 @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getAction.invoke(activityid, offset, size);
  }


}
