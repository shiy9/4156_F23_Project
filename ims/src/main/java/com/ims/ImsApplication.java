package com.ims;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Simple main() to start the Spring Application.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.ims.mapper")
public class ImsApplication {
  public static void main(String[] args) {
    SpringApplication.run(ImsApplication.class, args);
  }
}
