package cs3500.pokerpolygons.model.hw02;

/** Enum value for the Suit of the PokerCard. */
public enum Suit {
  HEART('♡'),
  SPADE('♠'),
  DIAMOND('♢'),
  CLUB('♣');
  private final char suitChar;

  /**
   * Constructor for the Suit enum.
   *
   * @param suitChar the char value of the suit.
   */
  Suit(char suitChar) {
    this.suitChar = suitChar;
  }

  /**
   * Getter value for the suitChar.
   *
   * @return the suitChar.
   */
  public char getSuitChar() {
    return suitChar;
  }
}
