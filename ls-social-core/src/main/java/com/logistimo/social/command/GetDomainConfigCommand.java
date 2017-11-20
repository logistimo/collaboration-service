/*
 * Copyright © 2017 Logistimo.
 *
 * This file is part of Logistimo.
 *
 * Logistimo software is a mobile & web platform for supply chain management and remote temperature monitoring in
 * low-resource settings, made available under the terms of the GNU Affero General Public License (AGPL).
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 *
 * You can be released from the requirements of the license by purchasing a commercial license. To know more about
 * the commercial license, please contact us at opensource@logistimo.com
 *
 */

package com.logistimo.social.command;

import com.logistimo.social.core.model.DomainModel;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * Created by kumargaurav on 04/08/17.
 */
public class GetDomainConfigCommand extends HystrixCommand<DomainModel> {

  final private RestTemplate restClient;
  final private Long domainId;
  final private String userId;
  final private String url;
  final static private String EVENT_CLIENT = "SocialServiceClient";
  final static private Integer TIMED_OUT = 5000;

  public GetDomainConfigCommand(RestTemplate restClient, Long domainId, String userId,
                                String url) {
    super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(EVENT_CLIENT))
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter().
                withExecutionTimeoutInMilliseconds(TIMED_OUT)));
    this.restClient = restClient;
    this.domainId = domainId;
    this.userId = userId;
    this.url = url + "/config/domain/general?domain_id=" + domainId;
  }

  private HttpEntity<String> getRequest() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-access-user", this.userId);
    return new HttpEntity<String>(headers);
  }

  @Override
  protected DomainModel run() throws Exception {
    UriComponents
        uriComponents =
        UriComponentsBuilder.fromHttpUrl(url).buildAndExpand();
    try {
      ResponseEntity<String>
          entity =
          restClient
              .exchange(uriComponents.toUri(), HttpMethod.GET, getRequest(), String.class);
      if (entity != null) {
        return map(entity.getBody());
      }
    } catch (HttpClientErrorException exception) {
      throw new HystrixBadRequestException(exception.getMessage(), exception);
    }
    return null;
  }

  private DomainModel map(String res) throws IOException {
    DomainModel dm = new DomainModel();
    JSONObject jsonObject = new JSONObject(res);
    dm.setDId(jsonObject.getLong("domainId"));
    dm.setCountry(jsonObject.getString("cnt"));
    dm.setState(jsonObject.getString("st"));
    dm.setDistrict(jsonObject.getString("ds"));
    dm.setLang(jsonObject.getString("lng"));
    return dm;
  }
}
