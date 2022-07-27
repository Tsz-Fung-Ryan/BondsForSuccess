package com.cic.config;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cic.service.MatchingService;
import com.cic.service.matching.BasicMatcher;
import com.cic.service.person.PersonKeys;
import com.cic.service.person.PersonService;

@Configuration
public class AppConfig {
  private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

  @Value("#{${mentee.key.mappings}}")
  Map<String, PersonKeys> menteeMapping;

  @Value("#{${mentee.key.mappings}}")
  Map<String, PersonKeys> mentorMapping;

  @Value("${acceptedThreshold}")
  float acceptedThreshold;

  @Bean
  BasicMatcher basicMatcher() {
    return new BasicMatcher();
  }

  @Bean
  MatchingService matchingService() {
    return new MatchingService(basicMatcher(), acceptedThreshold);
  }

  @Bean("menteeMapping")
  PersonService menteeService() {
    return new PersonService(menteeMapping);
  }

  @Bean("mentorMapping")
  PersonService mentorService() {
    return new PersonService(mentorMapping);
  }
}
