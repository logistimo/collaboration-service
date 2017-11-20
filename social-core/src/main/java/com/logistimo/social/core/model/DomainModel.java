package com.logistimo.social.core.model;

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
