package com.logistimo.social.core.model;

import java.util.Map;

import lombok.Data;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Data
public class ContentQuerySpecs {

  private String category;
  private String eventty;
  private String subeventty;
  private Map<String, String> variables;

  public ContentQuerySpecs(String category, String eventty, String subeventty,
                           Map<String, String> variables) {
    this.category = category;
    this.eventty = eventty;
    this.subeventty = subeventty;
    this.variables = variables;
  }

  private static class ContentVariable<T> {
     String type;
     T value;
  }
}
