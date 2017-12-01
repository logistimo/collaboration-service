package com.logistimo.collaboration.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.Resource;

/**
 * Created by kumargaurav on 14/02/17.
 */
@Configuration
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@EnableJpaRepositories(
    entityManagerFactoryRef = "scEntityManagerFactory",
    transactionManagerRef = "scTransactionManager",
    basePackages = {"com.logistimo.collaboration.repositories"})
public class CollaborationDSConfig {

  @Resource
  Environment env;

  @Bean(name = "scEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory()
      throws PropertyVetoException {
    LocalContainerEntityManagerFactoryBean
        entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setDataSource(logistimoDataSource());
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

    // Hibernate properties
    Properties additionalProperties = new Properties();
    additionalProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    additionalProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    additionalProperties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    entityManagerFactory.setJpaProperties(additionalProperties);
    entityManagerFactory.setPackagesToScan("com.logistimo.collaboration.entities");
    return  entityManagerFactory;
  }

  @Bean(name = "scDataSource")
  public ComboPooledDataSource logistimoDataSource () throws PropertyVetoException {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setMinPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.min_size")));
    dataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("hibernate.c3p0.max_size")));
//  dataSource.setAcquireIncrement(acquireIncrement);
//  dataSource.setIdleConnectionTestPeriod(idleTestPeriod);
//  dataSource.setMaxStatements(maxStatements);
    dataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("hibernate.c3p0.idle_test_period")));
    dataSource.setJdbcUrl(env.getProperty("spring.social.db.url"));
    dataSource.setPassword(env.getProperty("spring.social.db.password"));
    dataSource.setUser(env.getProperty("spring.social.db.username"));
    dataSource.setDriverClass(env.getProperty("spring.social.db.driver"));
    return dataSource;
  }

  @Bean(name = "scTransactionManager")
  public PlatformTransactionManager transactionManager() throws PropertyVetoException {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

}
