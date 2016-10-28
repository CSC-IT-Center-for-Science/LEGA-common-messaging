package eu.crg.ega.microservice.messaging.sender;

import eu.crg.ega.microservice.dto.message.ServiceMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class RabbitMessageSenderImpl implements RabbitMessageSender {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void prepareMessageAndSend(String exchange, String routingKey, ServiceMessage message) {
    prepareMessageAndSend(exchange, routingKey, message, null);
  }

  @Override
  public void prepareMessageAndSend(String exchange, String routingKey, ServiceMessage message, Integer priority) {
    MessageProperties messageProperties = fillProperties(priority);

    MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
    Message messageWithProperties = messageConverter.toMessage(message, messageProperties);
    log.trace("Message to send: " + new String(messageWithProperties.getBody()));

    send(exchange, routingKey, messageWithProperties);
  }

  private MessageProperties fillProperties(Integer priority) {
    MessageProperties messageProperties = new MessageProperties();

    messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
    // Message is persisted to prevent messages losses due system failure
    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
    if (priority != null) {
      messageProperties.setPriority(priority);
    }
    return messageProperties;
  }

  @Override
  public Void send(String exchange, String routingkey, Message message) {
    rabbitTemplate.convertAndSend(exchange, routingkey, message);
    return null;
  }

}
