package com.cic.service.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cic.openapi.model.Gender;
import com.cic.openapi.model.GenderPreference;
import com.cic.openapi.model.Person;

class PersonServiceTest {
  private static Logger LOGGER = LoggerFactory.getLogger(PersonServiceTest.class);
  PersonService personService;

  @BeforeEach
  void init() {
    personService = new PersonService(testMenteeMapping(), listOfKeywords());
  }

  @Test
  @DisplayName("GIVEN a csv of survey data WHEN it is fed to the PersonService THEN a Set of people will be created")
  void convertMenteeFileToPersonTest() {
    List<Person> people = personService.convertEntriesToPeople(menteePath());
    assertTrue(people.size() > 0);
    people.forEach(person -> {
      if (person.getName().equals("Test Name")) {
        assertEquals("test25@queensu.ca", person.getEmailAddress());
        assertEquals(Gender.MALE, person.getGender());
        assertEquals(GenderPreference.NO_PREFERENCE, person.getGenderPreference());
      }
    });
  }

  @Test
  void convertMentorFileToPersonTest() {
    List<Person> people = personService.convertEntriesToPeople(mentorPath());
    assertTrue(people.size() > 0);
    people.forEach(person -> {
      if (person.getName().equals("Professor Test")) {
        assertEquals("12345@rogers.com", person.getEmailAddress());
        assertEquals(Gender.MALE, person.getGender());
        assertEquals(GenderPreference.NO_PREFERENCE, person.getGenderPreference());
      }
    });
  }

  private Path menteePath() {
    return Paths.get("src/test/resources/MenteeTestRaw.csv");
  }

  private Path mentorPath() {
    return Paths.get("src/test/resources/MentorTestRaw.csv");
  }

  private Map<String, PersonKeys> testMenteeMapping() {
    final Map<String, PersonKeys> menteeMapping = new HashMap<>();
    menteeMapping.put("What is your name?", PersonKeys.name);
    menteeMapping.put("Please enter your Email address.", PersonKeys.emailAddress);
    menteeMapping.put("Do you identify as:", PersonKeys.gender);
    menteeMapping.put("Would you like to be matched with someone of a specific gender?",
        PersonKeys.genderPreference);
    menteeMapping.put("What area are you studying? - Selected Choice", PersonKeys.keyWords);

    return menteeMapping;
  }

  private String[] listOfKeywords() {
    final String[] keyWords = {"Doctorate", "Master's", "Chemical Engineering", "Chemistry",
        "Industry", "Financial", "Managerial", "Research and Development", "Hiring", "Resume",
        "Government", "Technical", "Entrepreneur", "Start-up", "Academia", "Networking",
        "Alternative", "Career", "Green", "Leadership", "Scale up", "Transition", "Job Seeking",
        "Pharma", "Bio", "Safety", "Process", "Analytical"};
    return keyWords;
  }
}
