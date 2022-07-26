package com.cic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.matching.MatchingAlgorithm;

public class MatchingService {

  private final MatchingAlgorithm matcher;
  private final float acceptedThreshold;

  public MatchingService(final MatchingAlgorithm matcher, final float acceptedThreshold) {
    this.matcher = matcher;
    this.acceptedThreshold = acceptedThreshold;
  }

  public List<Match> returnMatches(final Set<Person> mentees, final Set<Person> mentors) {
    final List<Match> matches = new ArrayList<>();
    final Set<Person> tempMentors = mentors;
    mentees.forEach(mentee -> {
      Person mentor = findGoodMatch(mentee, tempMentors);
      Match match = new Match();
      match.setMentee(mentee);
      match.setMentor(mentor);
      matches.add(match);
      tempMentors.remove(mentor);
    });
    return matches;
  }

  private Person findGoodMatch(Person mentee, Set<Person> mentors) {
    for (Person mentor : mentors) {
      float threshold = matcher.matchingRatio(mentee, mentor);
      if (threshold >= acceptedThreshold) {
        return mentor;
      }
    }
    return null;
  }
}
