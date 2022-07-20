package com.cic.service.matching;

public abstract class MatchingAlgorithm {

  public MatchingAlgorithm() {}

  public abstract float matchingRatio(String[] participant1, String[] participant2);
}
