package com.example.jms.sender;

import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JmsSenderController {

  private final JmsTemplate jmsTemplate;

  public JmsSenderController(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @GetMapping("sendJmsMessage")
  public String sendMessage() {

    String queue = "test-queue2";

    jmsTemplate.send(queue, session -> {
      Message textMessage = session.createTextMessage("Jms Testing");
      textMessage.setStringProperty("Name", "Rahul");
      textMessage.setStringProperty("Age", "25");
      textMessage.setStringProperty("Country", "India");
      return textMessage;
    });
    log.info("Message sent to queue: {}", queue);
    return "Sent Successfully";
  }
}
