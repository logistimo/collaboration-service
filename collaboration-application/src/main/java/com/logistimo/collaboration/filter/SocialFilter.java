package com.logistimo.collaboration.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by kumargaurav on 09/11/17.
 */
public class SocialFilter extends GenericFilterBean {

  private static final Logger log = LoggerFactory.getLogger(SocialFilter.class);

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                       FilterChain filterChain) throws IOException, ServletException {

    log.info("Request for resource {}",((HttpServletRequest)servletRequest).getRequestURI());
    filterChain.doFilter(servletRequest,servletResponse);
  }
}
