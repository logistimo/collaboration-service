package com.logistimo.social.event.processor;

import com.logistimo.social.core.event.Event;
import com.logistimo.social.event.handler.Handler;
import com.logistimo.social.event.registry.HandlerRegistry;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Component
public class SocialEventProcessor<E extends Event> {

  @Resource
  HandlerRegistry registry;

  public void processEvent(E event) {

    List<Handler> handlers = registry.getEventHandlers(event);
    for (Handler handler:handlers) {
      handler.handle(event);
    }
  }

}
