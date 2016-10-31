package eu.crg.ega.microservice.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "rabbitMQ")
public class RabbitMqControlProperties {

  @NonNull
  private String host;

  @NonNull
  private String port;

  @NonNull
  private String virtualhost;

  @NonNull
  private String user;

  @NonNull
  private String password;


  @NonNull
  private String controlExchange;

  @NonNull
  private String controlQueue;

  @NonNull
  private String useSsl;

  @NonNull
  private String sslKeypassphrasePassword;

  @NonNull
  private String sslTrustpassphrasePassword;

  @NonNull
  private String sslKeypath;

  @NonNull
  private String sslTrustpath;
}
