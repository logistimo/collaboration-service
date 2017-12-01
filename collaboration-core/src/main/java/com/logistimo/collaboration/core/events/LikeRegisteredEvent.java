package com.logistimo.collaboration.core.events;

import com.logistimo.collaboration.core.constants.SocialType;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Data
public class LikeRegisteredEvent extends Event implements Serializable {

  private String objectId;

  private String objectType;

  private String contextId;

  private String contextType;

  private String user;

  private String src;

  private SocialType type;

  private String contextAttributes;

  public LikeRegisteredEvent () {
    super("logistimo");
  }
}
