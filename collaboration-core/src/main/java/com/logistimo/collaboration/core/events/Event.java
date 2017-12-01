package com.logistimo.collaboration.core.events;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Data
public abstract class Event implements Serializable {

  private String eventid;
  private String appname;

  public Event(String appname) {
    this.eventid = UUID.randomUUID().toString().replace("-","");
    this.appname = appname;
  }

}
