package com.ims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Simple main() to start the Spring Application.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ims.mapper")
@PropertySource("classpath:ims-secret.properties")
public class ImsApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImsApplication.class, args);
  }
}
