package cs3500.pokerpolygons.model.hw02;

import java.util.Objects;

/** Class that implements the PokerCard interface. */
public class StandardPokerCard implements PokerCard {
  private final Rank rank;
  private final Suit suit;

  /**
   * Constructor that takes a Rank and a Suit to make the StandardPokerCard.
   *
   * @param rank the Rank of the StandardPokerCard.
   * @param suit the Suit of the StandardPokerCard.
   * @throws IllegalArgumentException if the Rank or Suit are null.
   */
  public StandardPokerCard(Rank rank, Suit suit) {
    if (rank == null || suit == null) {
      throw new IllegalArgumentException("Invalid card data");
    }
    this.rank = rank;
    this.suit = suit;
  }

  @Override
  public Rank getRank() {
    return rank;
  }

  @Override
  public Suit getSuit() {
    return suit;
  }

  @Override
  public StandardPokerCard getCard() {
    return this;
  }

  @Override
  public String toString() {
    return rank.getRankString() + suit.getSuitChar();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof StandardPokerCard)) {
      return false;
    }
    StandardPokerCard that = (StandardPokerCard) other;
    return this.rank == that.rank && this.suit == that.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.toString());
  }
}
