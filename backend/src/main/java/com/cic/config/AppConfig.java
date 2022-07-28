package com.cic.config;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.cic.service.MatchingService;
import com.cic.service.data.DataService;
import com.cic.service.matching.BasicMatcher;
import com.cic.service.person.PersonKeys;
import com.cic.service.person.PersonService;

@Configuration
public class AppConfig implements WebMvcConfigurer {
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

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("*");
  }

  @Bean
  MatchingService matchingService() {
    return new MatchingService(basicMatcher(), acceptedThreshold);
  }

  @Bean
  PersonService menteeService() {
    return new PersonService(menteeMapping);
  }

  @Bean
  PersonService mentorService() {
    return new PersonService(mentorMapping);
  }

  @Bean
  DataService dataService() {
    return new DataService();
  }
}
