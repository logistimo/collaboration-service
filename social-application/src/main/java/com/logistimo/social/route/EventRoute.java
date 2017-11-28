package com.logistimo.social.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Component
public class EventRoute extends RouteBuilder {

//  @EndpointInject(uri = "jms:event")
//  Endpoint eventQ;

//  @Autowired
//  SocialEventProcessor processor;


  @Override
  public void configure() throws Exception {

//    from(eventQ)
//        .id(" Social event route")
//        .bean(processor,"processEvent");
  }
}
