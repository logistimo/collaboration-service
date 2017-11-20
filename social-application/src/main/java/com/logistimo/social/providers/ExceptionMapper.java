package com.logistimo.social.providers;

import com.logistimo.social.core.exception.ErrorResource;

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


  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResource> handleException(ValidationException exception, Locale locale)
      throws IOException {

    return new ResponseEntity<>(new ErrorResource(exception.getMessage(), "SC001", HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }
}
