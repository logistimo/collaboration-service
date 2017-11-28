package com.logistimo.social.controller;

import com.logistimo.social.action.GetCountForManyAction;
import com.logistimo.social.action.GetLikeForActivityAction;
import com.logistimo.social.action.GetLikerForActivityAction;
import com.logistimo.social.action.RegisterLikeAction;
import com.logistimo.social.core.model.GetLikeResponseModel;
import com.logistimo.social.core.model.LikeCountContainer;
import com.logistimo.social.core.model.LikerResponseModel;
import com.logistimo.social.core.model.RegisterLikeRequestModel;
import com.logistimo.social.core.model.RegisterLikeResponseModel;

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
@RequestMapping(path = "/likes")
public class SocialLikeController {

  private static final Logger logger = LoggerFactory.getLogger(SocialLikeController.class);

  @Resource
  RegisterLikeAction  createAction;

  @Resource
  GetLikerForActivityAction getLikerAction;

  @Resource
  GetCountForManyAction getCountForManyAction;

  @Resource
  GetLikeForActivityAction getLikeAction;


  @RequestMapping(path = "", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  RegisterLikeResponseModel registerLike (@RequestBody RegisterLikeRequestModel likeRequestModel) {

    return createAction.invoke(likeRequestModel);
  }

  @RequestMapping(path = "", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  GetLikeResponseModel getLikes (@RequestParam(name = "obj_id") String objid,
                                 @RequestParam(name = "obj_ty") String objty,
                                 @RequestParam(name = "contxt_id",required = false) String contxtid,
                                 @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                 @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikeAction.invoke(objid,objty,contxtid,offset,size);
  }

  @RequestMapping(path = "/{obj_ty}/{obj_id}/{contxt_id}/likers", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  LikerResponseModel getLikers (@PathVariable(name = "obj_id") String objid,
                                @PathVariable(name = "obj_ty") String objty,
                                @PathVariable(name = "contxt_id") String contxtid,
                                @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikerAction.invoke(objid,objty,contxtid, offset, size);
  }

  @RequestMapping(path = "/{obj_ty}/{obj_id}/likers", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  LikerResponseModel getLikers (@PathVariable(name = "obj_id") String objid,
                                @PathVariable(name = "obj_ty") String objty,
                                @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikerAction.invoke(objid,objty,null, offset, size);
  }

  @RequestMapping(path = "/counts", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  LikeCountContainer getLikesCount (@RequestBody LikeCountContainer request) {

    return getCountForManyAction.invoke(request);
  }

}
