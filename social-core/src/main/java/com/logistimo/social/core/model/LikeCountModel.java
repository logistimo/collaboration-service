package com.logistimo.social.core.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 22/11/17.
 */
@Data
public class LikeCountModel implements Serializable {

  private String objectId;

  private String objectType;

  private String contextId;

  private Integer count;

  private boolean liked;
}
