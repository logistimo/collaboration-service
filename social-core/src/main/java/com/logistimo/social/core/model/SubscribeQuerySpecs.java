package com.logistimo.social.core.model;

import lombok.Data;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Data
public class SubscribeQuerySpecs {

  private String objid;
  private String objty;
  private String contxtid;
  private String contxtty;
  private String subcontxtty;
  private String userid;

  public SubscribeQuerySpecs(String objid, String objty, String contxtid, String contxtty,
                             String userid) {
    this.objid = objid;
    this.objty = objty;
    this.contxtid = contxtid;
    this.contxtty = contxtty;
    this.userid = userid;
  }
}
