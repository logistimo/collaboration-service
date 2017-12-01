package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.RegisterLikeRequestModel;
import com.logistimo.collaboration.repositories.LikeRepository;
import com.logistimo.collaboration.repositories.SocialActivityRepository;
import com.logistimo.collaboration.repositories.SocialContextRepository;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.validation.Validator;

/**
 * Created by kumargaurav on 30/11/17.
 */
public class RegisterLikeActionTest {

  @Mock
  Validator validator;

  @Mock
  SocialContextRepository scRepository;

  @Mock
  SocialActivityRepository saRepository;

  @Mock
  LikeRepository lkRepository;

  @EndpointInject(uri="jms:collab-events")
  MockEndpoint end;

  @Produce(uri = "jms:collab-events")
  ProducerTemplate producer;

  @Test
  public void testInvoke () {

    RegisterLikeRequestModel re = createReqModel();
    Mockito.when(validator.validate(re)).thenReturn(null);
  }

  private RegisterLikeRequestModel createReqModel () {


    RegisterLikeRequestModel req = new RegisterLikeRequestModel();
    req.setObjectId("1");
    req.setObjectType("domain");
    req.setContextId("1100694");
    req.setContextType("event");
    req.setSrc("MMA");
    req.setUser("kumarg");
    return req;
  }
}
