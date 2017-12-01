package com.logistimo.collaboration.core.models;

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
  private String name;

}
