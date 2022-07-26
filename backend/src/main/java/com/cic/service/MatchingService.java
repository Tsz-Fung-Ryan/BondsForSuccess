package com.cic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.matching.MatchingAlgorithm;

public class MatchingService {

  private final MatchingAlgorithm matcher;

  public MatchingService(MatchingAlgorithm matcher) {
    this.matcher = matcher;
  }

  public List<Match> returnMatches(Set<Person> mentees, Set<Person> mentors) {
    List<Match> matches = new ArrayList<>();

    // for (int i = 0; i < mentees.length; i++) {
    // matches.add(new Match().mentor(mentees[i]).mentee(mentors[i]));
    // }

    return matches;
  }
}
