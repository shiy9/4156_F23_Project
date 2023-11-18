package com.ims.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The ApplicationContextConfig class defines the beans that will be used in the application.
 */
@Configuration
public class ApplicationContextConfig {
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }
}
