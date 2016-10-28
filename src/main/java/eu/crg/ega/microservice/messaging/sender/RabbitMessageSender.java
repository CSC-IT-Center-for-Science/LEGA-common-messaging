package eu.crg.ega.microservice.messaging.sender;

import eu.crg.ega.microservice.dto.message.ServiceMessage;
import eu.crg.ega.microservice.interfaces.Sender;

import org.springframework.amqp.core.Message;

public interface RabbitMessageSender extends Sender<Void, String, Message> {

  /**
   * Set message content and properties and sends the message. Setting properties include: content
   * type, delivery mode and time stamp.
   *
   * @param event : message content.
   */
  public void prepareMessageAndSend(String exchange, String routingkey, ServiceMessage event);

  public void prepareMessageAndSend(String exchange, String routingkey, ServiceMessage event, Integer priority);
}
