package com.logistimo.social.event.registry;

import com.logistimo.social.core.event.Event;
import com.logistimo.social.event.handler.Handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kumargaurav on 10/11/17.
 */
@Component
public class HandlerRegistry implements ApplicationListener<ContextRefreshedEvent> {


  private static final Logger log = LoggerFactory.getLogger(HandlerRegistry.class);
  @Autowired
  private ConfigurableListableBeanFactory beanFactory;

  private Map<String, Handler> handlers = new HashMap<>();

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public List<Handler> getEventHandlers(Event event) {
    List<Handler> handlers = new ArrayList<>();
    for(Handler handler : this.handlers.values()){
      if(getHandledEventType(handler).isAssignableFrom(event.getClass())){
        log.info("Queueing handler {} for {}", handler.getClass().getSimpleName(), event.getClass().getSimpleName());
        handlers.add(handler);
      }
    }
    return handlers;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    handlers.clear();
    handlers.putAll(beanFactory.getBeansOfType(Handler.class));
  }

  private Class<?> getHandledEventType(Handler handler) {
    Type genericSuperclass = handler.getClass().getGenericSuperclass();
    if (genericSuperclass instanceof ParameterizedType) {
      Class<?> genericType = getGenericType((ParameterizedType) genericSuperclass);
      if (genericType != null && Event.class.isAssignableFrom(genericType)) return genericType;
    }
    for (Type genericInterface : handler.getClass().getGenericInterfaces()) {
      if (genericInterface instanceof ParameterizedType) {
        Class<?> genericType = getGenericType((ParameterizedType) genericInterface);
        if (genericType != null && Event.class.isAssignableFrom(genericType)) return genericType;
      }
    }
    return null;
  }

  private Class<?> getGenericType(ParameterizedType type) {
    return (Class<?>) type.getActualTypeArguments()[0];
  }

}
