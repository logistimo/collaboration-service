package com.logistimo.social.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.logistimo.social.core.model.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Created by kumargaurav on 15/11/17.
 */
public class GetSubscriberCommand extends HystrixCommand<List<User>> {


  final private RestTemplate restClient;
  final private String url;
  final private String objectid;
  final private String objectty;
  final private String userTags;
  final private String userId;
  final static private String EVENT_CLIENT = "SocialServiceClient";
  final static private Integer TIMED_OUT = 5000;


  public GetSubscriberCommand(RestTemplate restClient, String url, String objectid, String objectty,
                              String userId, List<String> userTags) {
    super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(EVENT_CLIENT))
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().
                withExecutionTimeoutInMilliseconds(TIMED_OUT)));
    this.restClient = restClient;
    this.objectid = objectid;
    this.objectty = objectty;
    this.userTags = getUsersTagCSV(userTags);
    this.userId = userId;
    this.url = constructURL(url, this.objectid, this.userTags, this.objectty);
  }

  @Override
  public List<User> run() throws Exception {
    UriComponents
        uriComponents =
        UriComponentsBuilder.fromHttpUrl(url).buildAndExpand(objectid, userTags);
    try {
      ResponseEntity<String>
          entity =
          restClient.exchange(uriComponents.toUri(), HttpMethod.GET, getRequest(), String.class);
      if (entity != null) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(entity.getBody(), TypeFactory.defaultInstance()
            .constructCollectionType(List.class, User.class));
      }
    } catch (HttpClientErrorException exception) {
      throw new HystrixBadRequestException(exception.getMessage(), exception);
    }
    return Collections.emptyList();
  }

  private HttpEntity<String> getRequest() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-access-user", this.userId);
    return new HttpEntity<>(headers);
  }

  public String getUsersTagCSV(List<String> userTags) {
    StringBuilder tag = new StringBuilder("");
    if (userTags != null && !userTags.isEmpty()) {
      for (String userTag : userTags) {
        if (!StringUtils.isEmpty(tag.toString())) {
          tag.append(",");
        }
        tag.append("'").append(userTag).append("'");
      }
    }
    return tag.toString();
  }

  public String constructURL(String baseUrl, String objectId, String userTags, String objectType) {
    return baseUrl + "/users/users-by-tag?objectId=" + objectId + "&objectType=" + objectType + "&userTag=" + userTags;
  }


}
