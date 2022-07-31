package com.cic.service.person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.cic.openapi.model.Gender;
import com.cic.openapi.model.GenderPreference;
import com.cic.openapi.model.Person;

public class PersonService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
  private final Map<String, PersonKeys> mapping;
  private final String[] listOfKeywords;

  public PersonService(Map<String, PersonKeys> mapping, String[] listOfKeywords) {
    this.mapping = mapping;
    this.listOfKeywords = listOfKeywords;
  }

  public List<Person> convertEntriesToPeople(final Path path) {
    final List<Person> people = new ArrayList<>();

    try (FileReader fr = new FileReader(path.toString());
        BufferedReader br = new BufferedReader(fr)) {
      final String header = br.readLine();
      LOGGER.info(header);
      final Map<Integer, PersonKeys> headerMap = headerMappings(header);
      br.lines().forEach(line -> {
        final String[] personToBeMapped = line.split(",");
        final Person person = convertToPerson(headerMap, personToBeMapped);
        if (people.add(person)) {
          LOGGER.info("Person created: {}", person.toString());
        } else {
          LOGGER.error("Person failed to be added {}", person.toString());
        }
      });
      return people;
    } catch (IOException e) {
      LOGGER.error(e.toString());
      return null;
    }
  }

  private Person convertToPerson(final Map<Integer, PersonKeys> headerMap,
      final String[] personToBeMapped) {
    final Person person = new Person();
    headerMap.forEach((key, value) -> {
      if (key < personToBeMapped.length && personToBeMapped[key] != null
          && !personToBeMapped[key].isEmpty()) {
        final String personAttribute = personToBeMapped[key].replaceAll("^\"|\"$", "");
        switch (value) {
          case name:
            person.setName(personAttribute);
            break;
          case emailAddress:
            person.setEmailAddress(personAttribute);
            break;
          case keyWords:
            for (String keyword : listOfKeywords) {
              if (personAttribute.contains(keyword)) {
                person.addKeywordsItem(keyword);
                break;
              }
            }
            break;
          case gender:
            person.setGender(Gender.fromValue(personAttribute));
            break;
          case genderPreference:
            person.setGenderPreference(GenderPreference.fromValue(personAttribute));
            break;
        }
      }
    });
    return person;
  }

  private Map<Integer, PersonKeys> headerMappings(final String headers) {
    final String[] columns = headers.split(",");
    final Map<Integer, PersonKeys> headerMap = new HashMap<>();

    for (int i = 0; i < columns.length; i++) {
      if (mapping.containsKey(columns[i].replaceAll("^\"|\"$", ""))) {
        headerMap.put(Integer.valueOf(i), mapping.get(columns[i].replaceAll("^\"|\"$", "")));
      }
    }

    if (headerMap.isEmpty()) {
      LOGGER.error("Map could not be created please check listed properties");
      return null;
    }
    return headerMap;
  }

  public List<Person> convertFileToPeople(MultipartFile file) {
    File tempTable = new File("src/main/resources/initTable.tmp");
    try (OutputStream os = new FileOutputStream(tempTable)) {
      os.write(file.getBytes());
      return convertEntriesToPeople(tempTable.toPath());
    } catch (Exception e) {
      LOGGER.error("Error occured creating people: ", e);
      return null;
    }
  }
}
