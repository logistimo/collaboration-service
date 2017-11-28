package com.logistimo.social.core.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 14/11/17.
 */

@Data
public class SocialLikerModel implements Serializable {

  private String id;

  private String liker;

  private String cron;

  private String src;
}
