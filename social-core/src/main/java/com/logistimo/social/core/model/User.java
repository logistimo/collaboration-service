package com.logistimo.social.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

/**
 * Created by kumargaurav on 08/11/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class User {

  private String id;
  private String userId;
  private String email;
  private String phn;
  private String userNm;

}
