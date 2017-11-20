package com.logistimo.social.util;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Component
public class Util {

  @Produce(uri = "jms:test")
  private ProducerTemplate testQueueProducer;

  public void sendMessage(String msg) {
    testQueueProducer.sendBody(msg);
  }
}
