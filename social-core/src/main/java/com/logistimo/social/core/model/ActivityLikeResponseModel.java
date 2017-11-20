package com.logistimo.social.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kumargaurav on 09/11/17.
 */
public class ActivityLikeResponseModel  extends  PaginatedResponse implements Serializable {

  public List<SocialLikeModel> likes;

}
