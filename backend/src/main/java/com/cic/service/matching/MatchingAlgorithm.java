package com.cic.service.matching;

import java.util.List;
import com.cic.openapi.model.Person;

public interface MatchingAlgorithm {

  public abstract Person findMentorForMentee(final Person participant1,
      final List<Person> unmatchedMentors);
}
