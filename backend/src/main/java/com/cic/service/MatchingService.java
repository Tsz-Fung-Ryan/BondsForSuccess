package com.cic.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.matching.MatchingAlgorithm;

public class MatchingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(MatchingService.class);
  private final MatchingAlgorithm matcher;

  public MatchingService(final MatchingAlgorithm matcher) {
    this.matcher = matcher;
  }

  public List<Match> returnMatches(final List<Person> mentees, final List<Person> mentors) {
    final List<Match> matches = new ArrayList<>();
    final List<Person> unmatchedMentors = new ArrayList<>();
    unmatchedMentors.addAll(mentors);
    LOGGER.info("Mentors to be matched: {} Number of mentors: {}", unmatchedMentors.toString(),
        unmatchedMentors.size());
    for (Person mentee : mentees) {
      final List<Person> tempMentorList = new ArrayList<>();
      tempMentorList.addAll(unmatchedMentors);
      final Person mentor = matcher.findMentorForMentee(mentee, tempMentorList);
      final Match match = new Match();
      match.setMentee(mentee);
      match.setMentor(mentor);
      matches.add(match);
      for (Person mentorToBeRemoved : unmatchedMentors) {
        if (mentorToBeRemoved.equals(mentor)) {
          if (unmatchedMentors.remove(mentorToBeRemoved)) {
            LOGGER.info("Removed: {} size: {}", mentorToBeRemoved.getName(),
                unmatchedMentors.size());
            break;
          } else {
            LOGGER.error("Unable to remove mentor: {} size: {}", mentorToBeRemoved.getName(),
                unmatchedMentors.size());
            break;
          }
        }
      }
    }
    return matches;
  }
}
