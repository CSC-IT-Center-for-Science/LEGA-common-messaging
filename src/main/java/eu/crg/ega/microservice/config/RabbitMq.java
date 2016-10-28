package eu.crg.ega.microservice.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import lombok.extern.log4j.Log4j;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.crg.ega.microservice.dto.message.ServiceMessage;

@Log4j
@Lazy
@Configuration
public class RabbitMq {

  @Autowired
  private ObjectMapper objectMapper;

  @Bean
  public MessageConverter jsonMessageConverter() {

    Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();

    DefaultClassMapper classMapper = new DefaultClassMapper();
    classMapper.setDefaultType(ServiceMessage.class);
    jsonMessageConverter.setClassMapper(classMapper);
    jsonMessageConverter.setJsonObjectMapper(objectMapper);

    return jsonMessageConverter;
  }

  public void configureSSL(com.rabbitmq.client.ConnectionFactory factory, String useSsl,
      String SslKeypassphrasePassword, String SslKeypath, String SslTrustpassphrasePassword,
      String SslTrustpath) {
    try {
      if (Boolean.parseBoolean(useSsl)) {
        char[] keyPassphrase = SslKeypassphrasePassword.toCharArray();
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(SslKeypath), keyPassphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, keyPassphrase);

        char[] trustPassphrase = SslTrustpassphrasePassword.toCharArray();
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream(SslTrustpath), trustPassphrase);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);

        SSLContext c = SSLContext.getInstance("SSLv3");
        c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        factory.useSslProtocol(c);
      }
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException
        | CertificateException | IOException | KeyManagementException e) {
      log.error("Exception configuring SSL RabbitMQ connection", e);
    }
  }
  
}
