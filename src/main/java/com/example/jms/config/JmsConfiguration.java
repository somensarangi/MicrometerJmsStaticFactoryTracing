package com.example.jms.config;

import com.example.jms.config.JmsProperties.ActivemqProperties;
import io.micrometer.observation.ObservationRegistry;
import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableConfigurationProperties(JmsProperties.class)
@EnableJms
@ConditionalOnProperty(prefix = "amqp-jms.activemq", name = "broker-url")
public class JmsConfiguration {

  private final JmsProperties jmsProperties;

  public JmsConfiguration(JmsProperties jmsProperties) {
    this.jmsProperties = jmsProperties;
  }

  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
      ObservationRegistry observationRegistry, ConnectionFactory connectionFactory) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setObservationRegistry(observationRegistry);
    return factory;
  }

  @Bean
  public ConnectionFactory connectionFactory() throws Exception {
    ActiveMQConnectionFactory connectionFactory = setupConnectionFactory();

    return connectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate(ObservationRegistry observationRegistry,
      ConnectionFactory connectionFactory) {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
    jmsTemplate.setObservationRegistry(observationRegistry);
    return jmsTemplate;
  }

  private ActiveMQConnectionFactory setupConnectionFactory() {
    ActivemqProperties activeMqProperties = jmsProperties.getActivemq();

    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(activeMqProperties.getBrokerUrl());
    connectionFactory.setUserName(activeMqProperties.getUser());
    connectionFactory.setPassword(activeMqProperties.getPassword());
    connectionFactory.setUseAsyncSend(true);

    return connectionFactory;
  }
}