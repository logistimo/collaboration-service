package com.logistimo.social.core.model;

import java.io.Serializable;

/**
 * Created by kumargaurav on 14/11/17.
 */
public class PaginatedResponse implements Serializable {

  public int total;

  public int size;

  public int offset;

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }
}
