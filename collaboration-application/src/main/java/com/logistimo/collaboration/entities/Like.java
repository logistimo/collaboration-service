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
@Table(name = "SOCIALLIKE")
public class Like implements Serializable {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  @Column(name = "SCONTXTID")
  private String scontxtid;

  @Column(name = "SACTVTYID")
  private String sactvtyid;

  @Column(name = "LIKER")
  private String liker;

  @Column(name = "CRON")
  private Date cron;

  @Column(name = "SRC")
  private String src;

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

  public String getSactvtyid() {
    return sactvtyid;
  }

  public void setSactvtyid(String sactvtyid) {
    this.sactvtyid = sactvtyid;
  }

  public String getLiker() {
    return liker;
  }

  public void setLiker(String liker) {
    this.liker = liker;
  }

  public Date getCron() {
    return cron;
  }

  public void setCron(Date cron) {
    this.cron = cron;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }
}
