package com.logistimo.social.core.model;

import lombok.Data;
import lombok.ToString;

/**
 * Created by kumargaurav on 08/11/17.
 */

@Data
@ToString
public class User {

  private String id;
  private String userId;
  private String email;
  private String phn;
  private String userNm;

}
