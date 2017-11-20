package com.logistimo.social.route;

import com.logistimo.social.core.event.Event;
import com.logistimo.social.event.processor.SocialEventProcessor;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Component
public class EventRoute extends RouteBuilder {

  @EndpointInject(uri = "jms:event")
  Endpoint eventQ;

  @Autowired
  SocialEventProcessor processor;


  @Override
  public void configure() throws Exception {

    from(eventQ)
        .id(" Social event route")
        .bean(processor,"processEvent");
  }
}
