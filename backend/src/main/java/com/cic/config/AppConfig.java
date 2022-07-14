package com.cic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cic.service.MatchingService;
import com.cic.service.PersonService;

@Configuration
public class AppConfig {

  @Bean
  MatchingService matchingService() {
    return new MatchingService(null);
  }

  @Bean
  PersonService personService() {
    return new PersonService();
  }
}
