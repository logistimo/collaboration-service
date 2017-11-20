package com.logistimo.social.contentgenerator;

import com.logistimo.social.core.contentgenerator.IContentGenerator;
import com.logistimo.social.core.model.ContentQuerySpecs;

import org.springframework.stereotype.Component;

/**
 * Created by kumargaurav on 13/11/17.
 */
@Component(value = "lsContentGenerator")
public class LSContentGenerator implements IContentGenerator {


  @Override
  public String getContent(ContentQuerySpecs specs) {
    return "You have liked it!";
  }
}
