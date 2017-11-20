package com.logistimo.social.core.provider;

import com.logistimo.social.core.model.SubscribeQuerySpecs;
import com.logistimo.social.core.model.User;

import java.util.List;

/**
 * Created by kumargaurav on 08/11/17.
 */
public interface ISubscriberProvider {

  List<User> getNotificationSubscriber (SubscribeQuerySpecs specs);
}
