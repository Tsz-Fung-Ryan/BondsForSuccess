package com.cic.service.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cic.openapi.model.Person;

class PersonServiceTest {
  private static Logger LOGGER = LoggerFactory.getLogger(PersonServiceTest.class);
  PersonService personService;

  @BeforeEach
  void init() {
    LOGGER.info(testMenteeMapping().toString());
    personService = new PersonService(testMenteeMapping());
  }

  @Test
  @DisplayName("GIVEN a csv of survey data WHEN it is fed to the PersonService THEN a Set of people will be created")
  void convertFileToPersonTest() {
    Set<Person> people = personService.convertEntriesToPeople(menteePath());
    assertTrue(people.size() > 0);
    people.forEach(person -> {
      if (person.getName() == "Test Name") {
        assertEquals("test25@queensu.ca", person.getEmailAddress());
      }
    });
  }

  private Path menteePath() {
    return Paths.get("src/test/resources/MenteeTestRaw.csv");
  }

  private Map<String, PersonKeys> testMenteeMapping() {
    final Map<String, PersonKeys> menteeMapping = new HashMap<>();
    menteeMapping.put("What is your name?", PersonKeys.name);
    menteeMapping.put("Please enter your Email address", PersonKeys.emailAddress);

    return menteeMapping;
  }
}
