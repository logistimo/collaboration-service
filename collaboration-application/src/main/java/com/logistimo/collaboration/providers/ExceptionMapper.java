package com.logistimo.collaboration.providers;

import com.logistimo.collaboration.core.exceptions.ErrorResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Locale;

import javax.validation.ValidationException;

/**
 * Created by kumargaurav on 09/11/17.
 */
@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(ExceptionMapper.class);

  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResource> handleException(ValidationException exception, Locale locale)
      throws IOException {
    log.error("Validation Error occurred {}",exception.getMessage());
    return new ResponseEntity<>(new ErrorResource(exception.getMessage(), "SC001", HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ResponseBody
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResource> handleException(Exception exception, Locale locale)
      throws IOException {
    log.error("System Error occurred {}",exception.getMessage());
    return new ResponseEntity<>(new ErrorResource(exception.getMessage(), "SC002", HttpStatus.INTERNAL_SERVER_ERROR.value()),
        HttpStatus.BAD_REQUEST);
  }
}
