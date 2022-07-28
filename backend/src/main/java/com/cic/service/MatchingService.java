package com.cic.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.cic.openapi.model.GenderPreference;
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
    final Set<Person> unmatchedMentors = mentors;
    mentees.forEach(mentee -> {
      Person mentor = findMentorForMentee(mentee, unmatchedMentors);
      Match match = new Match();
      match.setMentee(mentee);
      match.setMentor(mentor);
      matches.add(match);
      unmatchedMentors.remove(mentor);
    });
    return matches;
  }

  private Person findMentorForMentee(final Person mentee, final Set<Person> mentors) {
    Set<Person> filteredMentors = new HashSet<>();
    if (mentee.getGenderPreference().equals(GenderPreference.NO_PREFERENCE)) {
      filteredMentors.addAll(mentors);
    } else {
      filteredMentors = mentors.stream().filter(
          mentor -> mentor.getGender().getValue().equals(mentee.getGenderPreference().getValue()))
          .collect(Collectors.toSet());
    }

    for (Person mentor : filteredMentors) {
      float threshold = matcher.matchingRatio(mentee, mentor);
      if (threshold >= acceptedThreshold) {
        return mentor;
      }
    }
    return null;
  }
}
