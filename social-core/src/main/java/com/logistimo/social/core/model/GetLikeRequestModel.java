package com.logistimo.social.core.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Data
public class GetLikeRequestModel implements Serializable {

  private String objectId;

  private String objectType;

  private String contextId;

  private String contextAttribute;
}
