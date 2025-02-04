package cs3500.pokerpolygons.model.hw02;

/** Interface determining the design of PokerCards. */
public interface PokerCard extends Card {
  String toString();

  /**
   * Gets the Rank of the PokerCard.
   *
   * @return the Rank of the PokerCard.
   */
  Rank getRank();

  /**
   * Gets the Suit of the PokerCard.
   *
   * @return the Suit of the PokerCard.
   */
  Suit getSuit();

  /**
   * Determines if two PokerCards are equal.
   *
   * @param o the thing the PokerCard is being compared with.
   * @return boolean of whether or not it is equals.
   */
  boolean equals(Object o);

  /**
   * Returns the HashCode of the PokerCard.
   *
   * @return the HashCode of the PokerCard.
   */
  int hashCode();

  /**
   * Gets the PokerCard.
   *
   * @return the PokerCard.
   */
  PokerCard getCard();
}
