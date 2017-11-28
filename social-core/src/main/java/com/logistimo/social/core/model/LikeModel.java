package com.logistimo.social.core.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 21/11/17.
 */
@Data
public class LikeModel implements Serializable {

  private String objectId;

  private String objectType;

  private String contextId;

  private String contextType;

  private String contextAttributes;

  private String liker;

  private String cron;
}
