package com.logistimo.collaboration.entities;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Entity
@Table(name = "SOCIALCONTEXT")
public class SocialContext implements Serializable {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  @Column(name = "OBJID")
  private String objid;

  @Column(name = "OBJTY")
  private String objty;

  @Column(name = "CONTXTID")
  private String contextid;

  @Column(name = "CONTXTTY")
  private String contextty;

  @Column(name = "CONTXTATTR")
  private String contextattr;

  @Column(name = "CRBY")
  private String crby;

  @Column(name = "CRON")
  private Date cron;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getObjid() {
    return objid;
  }

  public void setObjid(String objid) {
    this.objid = objid;
  }

  public String getObjty() {
    return objty;
  }

  public void setObjty(String objty) {
    this.objty = objty;
  }

  public String getContextid() {
    return contextid;
  }

  public void setContextid(String contextid) {
    this.contextid = contextid;
  }

  public String getContextty() {
    return contextty;
  }

  public void setContextty(String contextty) {
    this.contextty = contextty;
  }

  public String getContextattr() {
    return contextattr;
  }

  public void setContextattr(String contextattr) {
    this.contextattr = contextattr;
  }

  public String getCrby() {
    return crby;
  }

  public void setCrby(String crby) {
    this.crby = crby;
  }

  public Date getCron() {
    return cron;
  }

  public void setCron(Date cron) {
    this.cron = cron;
  }
}
