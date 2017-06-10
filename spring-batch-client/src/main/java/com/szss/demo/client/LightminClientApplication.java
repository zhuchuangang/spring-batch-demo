package com.szss.demo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.tuxdevelop.spring.batch.lightmin.client.configuration.EnableSpringBatchLightminClient;

@SpringBootApplication(exclude = {BatchAutoConfiguration.class})
@EnableSpringBatchLightminClient
public class LightminClientApplication {

  public static void main(final String[] args) {
    SpringApplication.run(LightminClientApplication.class, args);
  }

}
