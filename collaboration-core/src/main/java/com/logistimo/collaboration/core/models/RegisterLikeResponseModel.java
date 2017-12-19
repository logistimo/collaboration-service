package com.logistimo.collaboration.core.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Data
public class RegisterLikeResponseModel implements Serializable{

  private String objectId;

  private String objectType;

  private String contextId;

  private String contextType;

  private String contextAttribute;

  private List<SocialLikerModel> likers;

  private String activityId;

  private Integer counts;

  public RegisterLikeResponseModel() {
  }

  public RegisterLikeResponseModel(String objectId, String objectType, String contextId,
                                   String contextType, String activityId,
                                   String contextAttribute) {
    this.objectId = objectId;
    this.objectType = objectType;
    this.contextId = contextId;
    this.contextType = contextType;
    this.activityId = activityId;
    this.contextAttribute = contextAttribute;
  }
}
