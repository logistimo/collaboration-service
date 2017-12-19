package com.logistimo.collaboration.actions;

import com.logistimo.collaboration.core.models.RegisterLikeRequestModel;
import com.logistimo.collaboration.core.models.RegisterLikeResponseModel;
import com.logistimo.collaboration.service.LIkePersistenceService;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ValidationException;
import javax.validation.Validator;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * Created by kumargaurav on 30/11/17.
 */
public class RegisterLikeActionTest {

  @Mock
  Validator validator;

  @Mock
  LIkePersistenceService persistenceService;

  @Mock
  ProducerTemplate producer;

  RegisterLikeAction action;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    action = new RegisterLikeAction();
    action.setValidator(validator);
    action.setPersistenceService(persistenceService);
    action.setProducer(producer);
  }

  @Test
  public void testInvoke () {
    when(persistenceService.persist(any())).thenReturn(createResModel());
    assertNotNull(action.invoke(new RegisterLikeRequestModel()));
  }

  @Test(expected = ValidationException.class)
  public void testValidationException () {
    when(validator.validate(any())).thenThrow(new ValidationException());
    action.invoke(new RegisterLikeRequestModel());
  }

  private RegisterLikeResponseModel createResModel () {
    RegisterLikeResponseModel res = new RegisterLikeResponseModel();
    res.setObjectId("1");
    res.setObjectType("domain");
    res.setContextId("1100694");
    res.setContextType("event");
    res.setCounts(1);
    return res;
  }

}
