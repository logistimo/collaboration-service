package com.logistimo.social.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeResponseModel implements Serializable{

  private String objectid;

  private String objectty;

  private String contextid;

  private String contextty;

  private Map<String,String> contxtattr;

  private List<SocialLikeModel> likes;

  private String activityid;

  private Integer likecount;

  public LikeResponseModel(String objectid, String objectty, String contextid,
                           String contextty, String activityid,
                           Map<String, String> contxtattr) {
    this.objectid = objectid;
    this.objectty = objectty;
    this.contextid = contextid;
    this.contextty = contextty;
    this.activityid = activityid;
    this.contxtattr = contxtattr;
  }
}
