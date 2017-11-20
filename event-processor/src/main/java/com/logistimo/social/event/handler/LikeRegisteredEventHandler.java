package com.logistimo.social.event.handler;

import com.logistimo.social.core.contentgenerator.IContentGenerator;
import com.logistimo.social.core.event.LikeRegisteredEvent;
import com.logistimo.social.core.model.ContentQuerySpecs;
import com.logistimo.social.core.model.SubscribeQuerySpecs;
import com.logistimo.social.core.model.User;
import com.logistimo.social.core.notifier.INotifier;
import com.logistimo.social.core.provider.ISubscriberProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 13/11/17.
 */
@Component
public class LikeRegisteredEventHandler implements Handler<LikeRegisteredEvent> {

  private static final Logger log = LoggerFactory.getLogger(LikeRegisteredEventHandler.class);

  @Resource(name = "lsSubscriberProvider")
  ISubscriberProvider subscriberProvider;

  @Resource(name = "lsContentGenerator")
  IContentGenerator contentGenerator;

  @Resource(name="")
  INotifier sender;

  @Override
  public void handle(LikeRegisteredEvent event) {
    log.info("LikeRegisteredEvent {} being handled",event);
    List<User> users = getSubscribers(event);
    String content = getNotificationContent(event);
  }

  private List<User> getSubscribers(LikeRegisteredEvent event) {
    SubscribeQuerySpecs qspecs = new SubscribeQuerySpecs(event.getObjectid(),event.getObjectty(),event.getContextid(),event.getContextty(),event.getUser());
    return subscriberProvider.getNotificationSubscriber(qspecs);
  }

  private String getNotificationContent (LikeRegisteredEvent event) {
    String category = event.getContxtattr().get("category");
    String eventty = event.getContxtattr().get("type");
    String subeventty = event.getContxtattr().get("event_type");
    ContentQuerySpecs cspecs = new ContentQuerySpecs(category,eventty,subeventty,event.getContxtattr());
    return contentGenerator.getContent(cspecs);
  }
}
