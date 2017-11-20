package com.logistimo.social.command;

import com.logistimo.social.model.SMSModel;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by kumargaurav on 13/11/17.
 */
public class SendSMSCommand extends HystrixCommand<String> {

  final private RestTemplate restClient;
  final private String url;
  final private String userId;
  final private String content;
  final private String receivers;
  final static private String EVENT_CLIENT = "SocialServiceClient";
  final static private Integer TIMED_OUT = 5000;

  public SendSMSCommand (RestTemplate restClient, String url,
                         String userId, String content, String receivers) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(EVENT_CLIENT))
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().
                withExecutionTimeoutInMilliseconds(TIMED_OUT)));
    this.restClient = restClient;
    this.userId = userId;
    this.content = content;
    this.receivers = receivers;
    this.url = url +"/users/sendmessage/";
  }

  private HttpEntity<String> getRequest() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-access-user", this.userId);
    return new HttpEntity<String>(headers);
  }

  @Override
  protected String run() throws Exception {
    UriComponents
        uriComponents =
        UriComponentsBuilder.fromHttpUrl(url).buildAndExpand();
    try {
      ResponseEntity<String>
          entity =
          restClient
              .exchange(uriComponents.toUri(), HttpMethod.POST,  new HttpEntity<>(getModel()), String.class);
      if (entity != null) {
        return entity.getBody();
      }
    } catch (HttpClientErrorException exception) {
      throw new HystrixBadRequestException(exception.getMessage(), exception);
    }
    return null;
  }

  private SMSModel getModel () {
    return new SMSModel("sms","text",content,receivers);
  }
}




