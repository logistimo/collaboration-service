package com.logistimo.social.core.model;

import com.logistimo.social.core.constants.SocialType;

import java.io.Serializable;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Data
public class LikeRequestModel implements Serializable {

  @NotNull(message = "object id can not be null or empty!")
  private String objectid;

  @NotNull(message = "object type can not be null or empty!")
  private String objectty;

  @NotNull(message = "context id can not be null or empty!")
  private String contextid;

  @NotNull(message = "context id can not be null or empty!")
  private String contextty;

  @NotNull(message = "liker can not be null or empty!")
  private String user;

  @NotNull(message = "src can not be null or empty!")
  private String src;

  @NotNull(message = "type can not be null or empty!")
  private String type;

  private Map<String,String> contxtattr;

}
