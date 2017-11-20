package com.logistimo.social.core.contentgenerator;

import com.logistimo.social.core.model.ContentQuerySpecs;

/**
 * Created by kumargaurav on 08/11/17.
 */
public interface IContentGenerator {

  String getContent(ContentQuerySpecs specs);
}
