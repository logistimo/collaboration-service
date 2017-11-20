package com.logistimo.social.provider;

import com.logistimo.social.command.GetSubscriberCommand;
import com.logistimo.social.core.model.SubscribeQuerySpecs;
import com.logistimo.social.core.model.User;
import com.logistimo.social.core.provider.ISubscriberProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by kumargaurav on 14/11/17.
 */
@Component(value = "lsSubscriberProvider")
public class LSSubscriberProvider implements ISubscriberProvider {

  @Value("${logistimo.url}")
  private String url;

  RestTemplate restTemplate;

  @Override
  public List<User> getNotificationSubscriber(SubscribeQuerySpecs specs) {
    return get(specs);
  }

   private List<User> get (SubscribeQuerySpecs specs) {

     GetSubscriberCommand command =  new GetSubscriberCommand(restTemplate,url,specs.getObjid(),specs.getObjty(),specs.getUserid(),userTags(specs.getObjty()));
     return command.execute();
   }

   private List<String> userTags (String objectty) {
     if ("domain".equalsIgnoreCase(objectty)) {
       return Arrays.asList(new String[] {"DIO","SIO"});
     } else if ("store".equalsIgnoreCase(objectty)) {
       return Arrays.asList(new String[] {"CCP"});
     }
     return Collections.emptyList();
   }
}
