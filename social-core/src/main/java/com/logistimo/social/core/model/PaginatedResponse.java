package com.logistimo.social.core.model;

import java.io.Serializable;

/**
 * Created by kumargaurav on 14/11/17.
 */
public class PaginatedResponse implements Serializable {

  public int total;

  public int size;

  public int page;
}
