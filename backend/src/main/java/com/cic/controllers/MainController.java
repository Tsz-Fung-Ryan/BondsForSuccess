package com.cic.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import com.cic.openapi.api.CreateTableApi;
import com.cic.openapi.api.DownloadFileApi;
import com.cic.openapi.api.HelloWorldApi;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.MatchingService;
import com.cic.service.data.DataService;
import com.cic.service.person.PersonService;

@RestController
public class MainController implements HelloWorldApi, CreateTableApi, DownloadFileApi {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

  @Autowired
  PersonService menteeService;

  @Autowired
  PersonService mentorService;

  @Autowired
  MatchingService matchingService;

  @Autowired
  DataService dataService;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  public ResponseEntity<String> helloWorldGet() {
    return ResponseEntity.ok("Hello World");
  }

  @Override
  public ResponseEntity<List<Match>> createTablePost(MultipartFile menteeFile,
      MultipartFile mentorFile) {
    Set<Person> mentees = menteeService.convertFileToPeople(menteeFile);
    Set<Person> mentors = mentorService.convertFileToPeople(mentorFile);

    List<Match> matches = matchingService.returnMatches(mentees, mentors);

    return ResponseEntity.ok(matches);
  }

  @Override
  public ResponseEntity<Resource> downloadFilePost(List<Match> matches) {
    File csvFile = dataService.createFile(matches.toArray(new Match[0]));
    InputStreamResource resource;
    try {
      resource = new InputStreamResource(new FileInputStream(csvFile));
    } catch (FileNotFoundException e) {
      LOGGER.error("Error occured converting file to resource: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    return ResponseEntity.ok().body(resource);
  }
}
