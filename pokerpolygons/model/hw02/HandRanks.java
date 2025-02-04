package cs3500.pokerpolygons.model.hw02;

/** Enum Class determining the ranks of hands and their point values. */
public enum HandRanks {
  STRAIGHT_FLUSH(75),
  FOUR_OF_A_KIND(50),
  FULL_HOUSE(25),
  FLUSH(20),
  STRAIGHT(15),
  THREE_OF_A_KIND(10),
  TWO_PAIR(5),
  PAIR(2),
  HIGH_CARD(0);
  private final int points;

  /**
   * Constructor for the HandRanks class.
   *
   * @param points the point value of the HandRank.
   */
  HandRanks(int points) {
    this.points = points;
  }

  /**
   * Getter method for points.
   *
   * @return the number of points for each HandRank.
   */
  public int getPoints() {
    return points;
  }
}
