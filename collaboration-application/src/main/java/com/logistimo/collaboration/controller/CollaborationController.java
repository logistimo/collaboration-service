package com.logistimo.collaboration.controller;

import com.logistimo.collaboration.actions.GetCountForManyAction;
import com.logistimo.collaboration.actions.GetLikeForActivityAction;
import com.logistimo.collaboration.actions.GetLikerForActivityAction;
import com.logistimo.collaboration.actions.RegisterLikeAction;
import com.logistimo.collaboration.core.models.GetLikeResponseModel;
import com.logistimo.collaboration.core.models.LikeCountContainer;
import com.logistimo.collaboration.core.models.LikerResponseModel;
import com.logistimo.collaboration.core.models.RegisterLikeRequestModel;
import com.logistimo.collaboration.core.models.RegisterLikeResponseModel;

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
public class CollaborationController {

  private static final Logger logger = LoggerFactory.getLogger(CollaborationController.class);

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
  GetLikeResponseModel getLikes (@RequestParam(name = "object_id") String objid,
                                 @RequestParam(name = "object_type") String objty,
                                 @RequestParam(name = "context_id",required = false) String contxtid,
                                 @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                 @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikeAction.invoke(objid,objty,contxtid,offset,size);
  }

  @RequestMapping(path = "/{object_type}/{object_id}/{context_id}/likers", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  LikerResponseModel getLikers (@PathVariable(name = "object_id") String objectId,
                                @PathVariable(name = "object_type") String objectType,
                                @PathVariable(name = "context_id") String contextId,
                                @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikerAction.invoke(objectId,objectType,contextId, offset, size);
  }

  @RequestMapping(path = "/{object_type}/{object_id}/likers", method = RequestMethod.GET ,produces = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody
  LikerResponseModel getLikers (@PathVariable(name = "object_id") String objectId,
                                @PathVariable(name = "object_type") String objectType,
                                @RequestParam(name = "offset", required = false, defaultValue = "0") Integer offset,
                                @RequestParam(name = "size", required = false, defaultValue = "50") Integer size) {

    return getLikerAction.invoke(objectId,objectType,null, offset, size);
  }

  @RequestMapping(path = "/counts", method = RequestMethod.POST ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody
  LikeCountContainer getLikesCount (@RequestBody LikeCountContainer request) {

    return getCountForManyAction.invoke(request);
  }

}
