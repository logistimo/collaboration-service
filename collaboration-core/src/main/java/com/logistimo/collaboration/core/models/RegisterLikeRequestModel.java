package com.logistimo.collaboration.core.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Data
public class RegisterLikeRequestModel implements Serializable {

  @NotNull(message = "object id can not be null or empty!")
  private String objectId;

  @NotNull(message = "object type can not be null or empty!")
  private String objectType;

  @NotNull(message = "context id can not be null or empty!")
  private String contextId;

  @NotNull(message = "context id can not be null or empty!")
  private String contextType;

  @NotNull(message = "liker can not be null or empty!")
  private String user;

  @NotNull(message = "src can not be null or empty!")
  private String src;

  @NotNull(message = "type can not be null or empty!")
  private String type;

  private String contextAttribute;

}
