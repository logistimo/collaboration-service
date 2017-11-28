package com.logistimo.social.core.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by kumargaurav on 21/11/17.
 */
@Data
public class GetLikeResponseModel extends PaginatedResponse implements Serializable {

  private List<LikeModel> likes;

}
