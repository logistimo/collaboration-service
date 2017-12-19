package com.logistimo.collaboration.actions;

import com.google.gson.GsonBuilder;

import com.logistimo.collaboration.core.events.Event;
import com.logistimo.collaboration.core.events.LikeRegisteredEvent;
import com.logistimo.collaboration.core.models.RegisterLikeRequestModel;
import com.logistimo.collaboration.core.models.RegisterLikeResponseModel;
import com.logistimo.collaboration.service.LIkePersistenceService;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;

/**
 * Created by kumargaurav on 09/11/17.
 */
@Component
public class RegisterLikeAction {

  private static final Logger log = LoggerFactory.getLogger(RegisterLikeAction.class);

  Validator validator;

  LIkePersistenceService persistenceService;

  ProducerTemplate producer;


  @Transactional(transactionManager = "scTransactionManager",propagation = Propagation.REQUIRES_NEW)
  public RegisterLikeResponseModel invoke (RegisterLikeRequestModel likeRequestModel) {
    //validate request data
    validate(likeRequestModel);
    RegisterLikeResponseModel response =  persistenceService.persist(likeRequestModel);
    //raise events
    Event e = constructEvent(response, likeRequestModel.getUser());
    log.info("sending regitered event {}",e);
    producer.sendBody(getData(e));
    return response;
  }

  private void validate(RegisterLikeRequestModel model) {
    //validating the request data
    Set<ConstraintViolation<RegisterLikeRequestModel>> violations = validator.validate(model);
    if (!violations.isEmpty() && violations.size() > 0) {
      StringBuilder errBuilder = new StringBuilder();
      violations.forEach(violation -> errBuilder.append(violation.getMessage()));
      log.error("Invalid request with data {} and error {}", model, errBuilder.toString());
      throw new ValidationException(errBuilder.toString());
    }
    if (persistenceService.likeExists(model)) {
      log.warn("like exists with data {}", model);
      throw new ValidationException("like exists for object and likee");
    }
  }


  private Event constructEvent(RegisterLikeResponseModel response, String user) {
    LikeRegisteredEvent event = new LikeRegisteredEvent();
    event.setObjectId(response.getObjectId());
    event.setObjectType(response.getObjectType());
    event.setContextId(response.getContextId());
    event.setContextType(response.getContextType());
    event.setContextAttributes(response.getContextAttribute());
    event.setUser(user);
    return event;
  }

  private String getData (Event event) {
    return new GsonBuilder().create().toJson(event);
  }

  @Autowired
  public void setValidator(Validator validator) {
    this.validator = validator;
  }

  @Autowired
  public void setPersistenceService(
      LIkePersistenceService persistenceService) {
    this.persistenceService = persistenceService;
  }

  @Produce(uri = "jms:collab-events")
  public void setProducer(ProducerTemplate producer) {
    this.producer = producer;
  }
}
