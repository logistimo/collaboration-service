package com.logistimo.collaboration.config;

import com.logistimo.collaboration.filter.SocialFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * Created by kumargaurav on 03/03/17.
 */
@Configuration
public class SocialBeanConfig {

  @Bean
  public Validator validator() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  public FilterRegistrationBean filter() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new SocialFilter());
    registration.addUrlPatterns("/*");
    return registration;
  }
}
