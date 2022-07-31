package com.cic.service.matching;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cic.openapi.model.GenderPreference;
import com.cic.openapi.model.Person;

public class BasicMatcher implements MatchingAlgorithm {
  private static final Logger LOGGER = LoggerFactory.getLogger(BasicMatcher.class);
  private final float acceptedThreshold;

  public BasicMatcher(float acceptedThreshold) {
    this.acceptedThreshold = acceptedThreshold;
  }

  @Override
  public Person findMentorForMentee(final Person mentee, final List<Person> unmatchedMentors) {
    float threshold = 0;
    Person mentorWithHighestThreshold = unmatchedMentors.get(0);
    List<Person> mentorsToFind = new ArrayList<>();
    mentorsToFind.addAll(unmatchedMentors);
    final Set<String> menteeKeywords = mentee.getKeywords();
    final List<Person> viableMentors = findPoolOfMentors(mentee, unmatchedMentors);
    for (Person mentor : viableMentors) {
      final float ratio = findMatchingRatio(menteeKeywords, mentor.getKeywords());
      if (ratio >= acceptedThreshold) {
        return mentor;
      } else if (ratio > threshold) {
        threshold = ratio;
        mentorWithHighestThreshold = mentor;
      }
    }
    LOGGER.warn("Lower acceptance threshold please");
    return mentorWithHighestThreshold;
  }

  private float findMatchingRatio(final Set<String> menteeKeywords,
      final Set<String> mentorKeywords) {
    Set<String> keywords = new HashSet<>();
    keywords.addAll(menteeKeywords);
    if (mentorKeywords.size() > menteeKeywords.size()) {
      keywords.retainAll(mentorKeywords);
      return (float) keywords.size() / mentorKeywords.size() * 100;
    } else {
      mentorKeywords.retainAll(keywords);
      return (float) mentorKeywords.size() / keywords.size() * 100;
    }
  }

  private List<Person> findPoolOfMentors(final Person mentee, final List<Person> unmatchedMentors) {

    final List<Person> mentorsForMentee = viableCandidatesForMentee(mentee, unmatchedMentors);
    final List<Person> menteeMatchesMentor = viableCondidateForMentor(mentee, unmatchedMentors);

    if (mentorsForMentee.isEmpty() && menteeMatchesMentor.isEmpty()) {
      return unmatchedMentors;
    }
    if (mentorsForMentee.isEmpty()) {
      return menteeMatchesMentor;
    }
    if (menteeMatchesMentor.isEmpty()) {
      return mentorsForMentee;
    }

    mentorsForMentee.retainAll(menteeMatchesMentor);
    return mentorsForMentee;
  }

  private List<Person> viableCondidateForMentor(final Person mentee,
      final List<Person> unmatchedMentors) {
    final List<Person> mentorsThatMatchWithMentee = new ArrayList<>();
    unmatchedMentors.forEach(mentor -> {
      final GenderPreference mentorGenderPreference =
          mentor.getGenderPreference() == null ? GenderPreference.NO_PREFERENCE
              : mentor.getGenderPreference();
      if (mentorGenderPreference.equals(GenderPreference.NO_PREFERENCE)) {
        mentorsThatMatchWithMentee.add(mentor);
      } else if (mentorGenderPreference.toString().equals(mentor.getGender().toString())) {
        mentorsThatMatchWithMentee.add(mentor);
      }
    });
    return mentorsThatMatchWithMentee;
  }

  private List<Person> viableCandidatesForMentee(final Person mentee,
      final List<Person> unmatchedMentors) {
    if (mentee.getGenderPreference() != null
        && !mentee.getGenderPreference().equals(GenderPreference.NO_PREFERENCE)) {
      final List<Person> mentorsForMentee = unmatchedMentors.stream().filter(
          mentor -> mentor.getGender().getValue().equals(mentee.getGenderPreference().getValue()))
          .collect(Collectors.toList());
      return mentorsForMentee;
    } else {
      final List<Person> viableMentors = new ArrayList<>();
      viableMentors.addAll(unmatchedMentors);
      return viableMentors;
    }
  }
}
