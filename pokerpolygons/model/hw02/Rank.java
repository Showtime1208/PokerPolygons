package cs3500.pokerpolygons.model.hw02;

/** Enum containing the different ranks for the PokerCards. */
public enum Rank {
  // document the null use
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13),
  ACE("A", 14);
  private final String rankString;
  private final Integer val;

  /**
   * Constructor that creates a Rank.
   *
   * @param rankString the textual value of the Rank.
   * @param val the int value of the Rank.
   */
  Rank(String rankString, Integer val) {
    this.rankString = rankString;
    this.val = val;
  }

  /**
   * Getter method for the rankString.
   *
   * @return the rankString.
   */
  public String getRankString() {
    return rankString;
  }

  /**
   * Getter method for the value.
   *
   * @return the int val.
   */
  public Integer getval() {
    return val;
  }
}
