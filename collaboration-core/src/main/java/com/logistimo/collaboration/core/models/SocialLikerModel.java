package com.logistimo.collaboration.core.models;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 14/11/17.
 */

@Data
public class SocialLikerModel implements Serializable {

  private String id;

  private String liker;

  private String createdOn;

  private String src;
}
