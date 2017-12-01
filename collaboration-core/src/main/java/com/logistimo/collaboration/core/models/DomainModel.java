package com.logistimo.collaboration.core.models;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by kumargaurav on 15/11/17.
 */
@Data
public class DomainModel implements Serializable{

  private Long dId;
  private String country;
  private String state;
  private String district;
  private String lang;
}
