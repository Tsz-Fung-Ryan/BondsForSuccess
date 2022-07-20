package com.cic.service;

import java.util.ArrayList;
import java.util.List;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.matching.MatchingAlgorithm;

public class MatchingService {

  private final MatchingAlgorithm matcher;

  public MatchingService(MatchingAlgorithm matcher) {
    this.matcher = matcher;
  }

  public List<Match> returnMatches(Person[] mentors, Person[] mentee) {
    List<Match> matches = new ArrayList<>();

    for (int i = 0; i < mentors.length; i++) {
      matches.add(new Match().mentor(mentors[i]).mentee(mentee[i]));
    }

    return matches;
  }
}
