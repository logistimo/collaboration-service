package com.logistimo.collaboration.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;

/**
 * Created by kumargaurav on 08/11/17.
 */
@Configuration
public class JmsConfig {

  @Value("${activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public ActiveMQConnectionFactory connectionFactory () {
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
    connectionFactory.setTrustedPackages(Arrays.asList("com.logistimo.social.core.event"));
    return connectionFactory;
  }

  @Primary
  @Bean(initMethod = "start", destroyMethod = "stop")
  public PooledConnectionFactory pooledConnectionFactory () {
    PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
    pooledConnectionFactory.setMaxConnections(11);
    pooledConnectionFactory.setMaximumActiveSessionPerConnection(10);
    pooledConnectionFactory.setConnectionFactory(connectionFactory());
    pooledConnectionFactory.setIdleTimeout(0);
    return pooledConnectionFactory;
  }

  @Bean
  public JmsConfiguration jmsConfiguration () {
    JmsConfiguration jmsConfiguration = new JmsConfiguration();
    jmsConfiguration.setConnectionFactory(pooledConnectionFactory());
    jmsConfiguration.setRequestTimeout(5000);
    jmsConfiguration.setTransacted(true);
    jmsConfiguration.setConcurrentConsumers(10);
    jmsConfiguration.setDeliveryPersistent(true);
    jmsConfiguration.setCacheLevelName("CACHE_CONSUMER");
    return jmsConfiguration;
  }

  @Bean
  public ActiveMQComponent activeMQComponent () {
    ActiveMQComponent activeMQComponent = new ActiveMQComponent();
    activeMQComponent.setConfiguration(jmsConfiguration());
    return activeMQComponent;
  }
}

