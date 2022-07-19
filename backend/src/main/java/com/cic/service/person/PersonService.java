package com.cic.service.person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.cic.openapi.model.Person;

public class PersonService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private final Map<PersonKeys, String> menteeMapping;

  public PersonService(Map<PersonKeys, String> menteeMapping) {
    this.menteeMapping = menteeMapping;
  }

  public Person convertEntryToPerson(Path path) {
    LOGGER.info(menteeMapping.toString());
    try {
      Files.lines(path).forEach(line -> {
        LOGGER.info(line);
      });
    } catch (IOException e) {
      LOGGER.error(e.toString());
    }
    return null;
  }

  public Person[] convertFileToPerson(MultipartFile file) {
    return null;
  }
}
