package com.logistimo.collaboration.core.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kumargaurav on 09/11/17.
 */
public class LikerResponseModel extends  PaginatedResponse implements Serializable {

  private List<SocialLikerModel> likers;

  public List<SocialLikerModel> getLikers() {
    return likers;
  }

  public void setLikers(List<SocialLikerModel> likers) {
    this.likers = likers;
  }
}
