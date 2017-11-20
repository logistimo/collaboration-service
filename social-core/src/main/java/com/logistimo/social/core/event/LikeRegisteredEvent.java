package com.logistimo.social.core.event;

import com.logistimo.social.core.constants.SocialType;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Data
public class LikeRegisteredEvent extends Event implements Serializable {

  private String objectid;

  private String objectty;

  private String contextid;

  private String contextty;

  private String user;

  private String src;

  private SocialType type;

  private Map<String,String> contxtattr;

  public LikeRegisteredEvent () {
    super("logistimo");
  }
}
