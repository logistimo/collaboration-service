package com.logistimo.social.model;

import java.io.Serializable;

/**
 * Created by kumargaurav on 15/11/17.
 */
@lombok.Data
public class SMSModel implements Serializable{

  private String type;
  private String template;
  private String text;
  private String userIds;

  public SMSModel(String type, String template, String text, String userIds) {
    this.type = type;
    this.template = template;
    this.text = text;
    this.userIds = userIds;
  }
}
