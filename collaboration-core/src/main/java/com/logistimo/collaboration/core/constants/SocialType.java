package com.logistimo.collaboration.core.constants;

/**
 * Created by kumargaurav on 13/11/17.
 */
public enum SocialType {

  COMMENT("Comment"),
  LIKE("Like");

  private String value;

  SocialType (String value){
    this.value = value;
  }

  public static String getValue (String val){
    for(SocialType t: SocialType.values()) {
      if(t.value.equalsIgnoreCase(val)) {
        return t.name();
      }
    }
    return  null;
  }
}
