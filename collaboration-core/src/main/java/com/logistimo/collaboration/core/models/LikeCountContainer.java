package com.logistimo.collaboration.core.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by kumargaurav on 22/11/17.
 */
@Data
public class LikeCountContainer implements Serializable {

  private List<LikeCountModel> likes;

  private String user;
}
