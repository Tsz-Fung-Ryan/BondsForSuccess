package com.cic.config;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cic.service.MatchingService;
import com.cic.service.person.PersonKeys;
import com.cic.service.person.PersonService;

@Configuration
public class AppConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

  @Value("#{${mentee.key.mappings}}")
  Map<PersonKeys, String> menteeMapping;

  @Bean
  MatchingService matchingService() {
    return new MatchingService(null);
  }

  @Bean
  PersonService personService() {
    return new PersonService(menteeMapping);
  }
}
