package eu.crg.ega.microservice.utils;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMessageUtils {

  public static <T> Jackson2JsonMessageConverter jsonMessageConverter(Class<T> clazz,
                                                                      ObjectMapper objectMapper) {
    DefaultClassMapper classMapper = new DefaultClassMapper();
    classMapper.setDefaultType(clazz);

    Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
    jsonMessageConverter.setClassMapper(classMapper);

    jsonMessageConverter.setJsonObjectMapper(objectMapper);

    return jsonMessageConverter;
  }

}
