package com.cic.service.matching;

import com.cic.openapi.model.Person;

public class BasicMatcher extends MatchingAlgorithm {

  @Override
  public float matchingRatio(Person mentee, Person mentor) {
    return 100;
  }
}
