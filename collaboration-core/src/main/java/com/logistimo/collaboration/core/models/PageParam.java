package com.logistimo.collaboration.core.models;

import lombok.Data;

/**
 * Created by kumargaurav on 23/11/17.
 */
@Data
public class PageParam {

  private int page;

  private int size;

  public PageParam(int page, int size) {
    this.page = page;
    this.size = size;
  }
}
