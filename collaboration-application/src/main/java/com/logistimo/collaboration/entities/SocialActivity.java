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
@Table(name = "SOCIALACTIVITY")
public class SocialActivity implements Serializable {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  @Column(name = "SCONTXTID")
  private String scontxtid;

  @Column(name = "ACTIVITYTY")
  private String actvtyty;

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

  public String getScontxtid() {
    return scontxtid;
  }

  public void setScontxtid(String scontxtid) {
    this.scontxtid = scontxtid;
  }

  public String getActvtyty() {
    return actvtyty;
  }

  public void setActvtyty(String actvtyty) {
    this.actvtyty = actvtyty;
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
