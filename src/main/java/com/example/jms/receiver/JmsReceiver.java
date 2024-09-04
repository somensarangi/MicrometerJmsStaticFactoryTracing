package com.example.jms.receiver;


import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsReceiver {

  @JmsListener(destination = "test-queue2")
  public void receivedJmsMessage(Message message) {
    try {
      log.info("Received Message : {}", message);

    } catch (Exception e) {
      log.error("Error while processing Jms message", e);
    }
  }
}
