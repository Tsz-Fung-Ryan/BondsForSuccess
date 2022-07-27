package com.cic.service.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.cic.openapi.model.Gender;
import com.cic.openapi.model.GenderPreference;
import com.cic.openapi.model.Person;

public class PersonService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private final Map<String, PersonKeys> mapping;

  public PersonService(Map<String, PersonKeys> mapping) {
    this.mapping = mapping;
  }

  public Set<Person> convertEntriesToPeople(final Path path) {
    final Set<Person> people = new HashSet<>();

    try (FileReader fr = new FileReader(path.toString());
        BufferedReader br = new BufferedReader(fr)) {
      final String header = br.readLine();
      LOGGER.info(header);
      final Map<Integer, PersonKeys> headerMap = headerMappings(header);
      br.lines().forEach(line -> {
        final String[] personToBeMapped = line.split(",");
        people.add(convertToPerson(headerMap, personToBeMapped));
        LOGGER.info(line);
      });

    } catch (IOException e) {
      LOGGER.error(e.toString());
    }
    return people;
  }

  private Person convertToPerson(final Map<Integer, PersonKeys> headerMap,
      final String[] personToBeMapped) {
    final Person person = new Person();
    headerMap.forEach((key, value) -> {
      switch (value) {
        case name:
          person.setName(personToBeMapped[key]);
          break;
        case emailAddress:
          person.setEmailAddress(personToBeMapped[key]);
          break;
        case keyWords:
          person.addKeywordsItem(personToBeMapped[key]);
        case gender:
          person.setGender(Gender.fromValue(personToBeMapped[key]));
          break;
        case genderPreference:
          person.setGenderPreference(GenderPreference.fromValue(personToBeMapped[key]));
          break;
      }
    });
    return person;
  }

  private Map<Integer, PersonKeys> headerMappings(final String headers) {
    final String[] columns = headers.split(",");
    final Map<Integer, PersonKeys> headerMap = new HashMap<>();

    for (int i = 0; i < columns.length; i++) {
      if (mapping.containsKey(columns[i])) {
        headerMap.put(Integer.valueOf(i), mapping.get(columns[i]));
      }
    }

    if (headerMap.isEmpty()) {
      LOGGER.error("Map could not be created please check listed properties");
      return null;
    }
    return headerMap;
  }

  public Set<Person> convertFileToPeople(MultipartFile file) {
    File tempTable = new File("src/main/resources/initTable.tmp");
    try {
      file.transferTo(tempTable);
      return convertEntriesToPeople(tempTable.toPath());
    } catch (Exception e) {
      LOGGER.error("Error occured creating people: {}", e);
      return null;
    }
  }
}
