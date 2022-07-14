package com.cic.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import com.cic.openapi.api.CreateTableApi;
import com.cic.openapi.api.HelloWorldApi;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.MatchingService;
import com.cic.service.PersonService;

@RestController
public class MainController implements HelloWorldApi, CreateTableApi {

  @Autowired
  PersonService personService;

  @Autowired
  MatchingService matchingService;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  @GetMapping("/helloWorld")
  public ResponseEntity<String> helloWorldGet() {
    return ResponseEntity.ok("Hello World");
  }

  @Override
  @PostMapping("/createTable")
  public ResponseEntity<List<Match>> createTablePost(MultipartFile menteeFile,
      MultipartFile mentorFile) {
    Person[] mentees = personService.convertFileToPerson(menteeFile);
    Person[] mentors = personService.convertFileToPerson(mentorFile);

    List<Match> matches = matchingService.returnMatches(mentees, mentors);

    return ResponseEntity.ok(matches);
  }
}
