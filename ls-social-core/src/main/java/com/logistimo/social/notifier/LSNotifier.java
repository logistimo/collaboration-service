package com.logistimo.social.notifier;

import com.logistimo.social.command.SendSMSCommand;
import com.logistimo.social.core.constants.NotificationType;
import com.logistimo.social.core.model.User;
import com.logistimo.social.core.notifier.INotifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by kumargaurav on 15/11/17.
 */
@Component
public class LSNotifier implements INotifier {

  @Value("${logistimo.url}")
  private String url;

  RestTemplate restTemplate;

  @Override
  public String notify(List<User> users, String content, NotificationType type) {
    SendSMSCommand command = new SendSMSCommand(restTemplate, url, null, content, userIds(users));
    return command.execute();
  }

  private String userIds (List<User> users) {
    StringBuilder builder = new StringBuilder();
    users.forEach(user -> builder.append(user.getId()).append(","));
    builder.setLength(builder.length()-1);
    return builder.toString();
  }
}
