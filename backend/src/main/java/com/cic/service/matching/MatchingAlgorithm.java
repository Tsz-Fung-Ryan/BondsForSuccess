package com.cic.service.matching;

import com.cic.openapi.model.Person;

public abstract class MatchingAlgorithm {

  public MatchingAlgorithm() {}

  public abstract float matchingRatio(Person participant1, Person participant2);
}
