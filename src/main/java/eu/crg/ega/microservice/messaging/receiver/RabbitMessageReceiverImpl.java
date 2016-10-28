package eu.crg.ega.microservice.messaging.receiver;

import eu.crg.ega.microservice.dto.message.ServiceMessage;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j;

@Log4j
public class RabbitMessageReceiverImpl {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public ServiceMessage receive(Message message) {

    ServiceMessage event = convertFromMessage(message);

    return event;
  }

  /**
   * Converts content from JSON format to DTO class.
   */
  private ServiceMessage convertFromMessage(Message message) {
    log.trace("Message to read: " + new String(message.getBody()));

    MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
    ServiceMessage event = (ServiceMessage) messageConverter.fromMessage(message);
    return event;
  }

}
