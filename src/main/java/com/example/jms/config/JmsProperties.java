package com.example.jms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("amqp-jms")
public class JmsProperties {

  private ActivemqProperties activemq;

  @Data
  public static class ActivemqProperties {

    private String brokerUrl;
    private String user;
    private String password;
  }
}

