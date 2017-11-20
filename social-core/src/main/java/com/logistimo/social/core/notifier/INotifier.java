package com.logistimo.social.core.notifier;

import com.logistimo.social.core.constants.NotificationType;
import com.logistimo.social.core.model.User;

import java.util.List;

/**
 * Created by kumargaurav on 08/11/17.
 */
public interface INotifier {

  String notify (List<User> users, String content, NotificationType type);
}
