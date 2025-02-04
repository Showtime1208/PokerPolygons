package cs3500.pokerpolygons.model.hw02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * Model Class for the PokerPolygons implementation PokerTriangles. This game is played similar to
 * PokerSquares, but is played on an equilateral triangle and the outermost diagonal is also scored.
 * Enjoy!
 */
public class PokerTriangles implements PokerPolygons<PokerCard> {
  private final int sideLength;
  private final int totalSpaces;
  private final PokerCard[][] board;
  private final int score;
  private final List<PokerCard> hand;
  private final Random random;
  private final List<PokerCard> deck;
  private int totalFullSpaces = 0;
  private boolean gameStart = false;
  private boolean gameOver = false;

  /**
   * Constructor for a PokerTriangles model.
   *
   * @param sideLength the Side length of the triangle.
   */
  public PokerTriangles(int sideLength) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Sidelength must be a minimum of length 5");
    }
    this.board = new PokerCard[sideLength][sideLength];
    this.sideLength = sideLength;
    this.deck = new ArrayList<>();
    this.score = -1;
    this.random = new Random();
    this.hand = new ArrayList<>();
    this.totalSpaces = getFactorial(sideLength);
  }

  /**
   * Constructor for the PokerTriangles class with capability for Random control.
   *
   * @param sideLength the sidelength of the triangle.
   * @param random the Random seed used to control randomness.
   */
  public PokerTriangles(int sideLength, Random random) {
    if (sideLength < 5) {
      throw new IllegalArgumentException("Sidelength must be a minimum of length 5.");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null.");
    }
    this.board = new PokerCard[sideLength][sideLength];
    this.sideLength = sideLength;
    this.deck = new ArrayList<>();
    this.score = -1;
    this.random = random;
    this.totalSpaces = getFactorial(sideLength);
    this.hand = new ArrayList<>();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof PokerTriangles)) {
      return false;
    }
    PokerTriangles that = (PokerTriangles) other;
    return this.sideLength == that.sideLength;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode("Poker Triangles: l = " + sideLength);
  }

  @Override
  public List<PokerCard> getNewDeck() {
    List<PokerCard> newDeck = new ArrayList<>();
    for (Rank rank : Rank.values()) {
      for (Suit suit : Suit.values()) {
        newDeck.add(new StandardPokerCard(rank, suit));
      }
    }
    return newDeck;
  }

  @Override
  public int getScore() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    int totalScore = 0;
    for (int row = 0; row < sideLength; row++) {
      List<PokerCard> rowCards = new ArrayList<>();
      for (int col = 0; col <= row; col++) {
        if (board[row][col] != null) {
          rowCards.add(board[row][col]);
        }
      }
      totalScore += evaluateBestHand(rowCards);
    }
    for (int col = 0; col < sideLength; col++) {
      List<PokerCard> colCards = new ArrayList<>();
      for (int row = col; row < sideLength; row++) {
        if (board[row][col] != null) {
          colCards.add(board[row][col]);
        }
      }
      totalScore += evaluateBestHand(colCards);
    }
    List<PokerCard> diagCards = new ArrayList<>();
    for (int i = 0; i < sideLength; i++) {
      if (board[i][i] != null) {
        diagCards.add(board[i][i]);
      }
    }
    totalScore += evaluateBestHand(diagCards);
    return totalScore;
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (cardIdx >= hand.size() || cardIdx < 0) {
      throw new IllegalArgumentException("Invalid card index.");
    }
    if (!isValidPosition(row, col)) {
      throw new IllegalArgumentException("Invalid Position.");
    }
    if (board[row][col] != null) {
      throw new IllegalStateException("Space is already occupied");
    }
    board[row][col] = hand.get(cardIdx);
    totalFullSpaces++;
    hand.remove(cardIdx);
    if (deck.isEmpty()) {
      return;
    }
    hand.add(deck.get(0));
    deck.remove(0);
  }

  @Override
  public void discardCard(int cardIdx) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started yet.");
    }
    if (cardIdx >= hand.size() || cardIdx < 0) {
      throw new IllegalArgumentException("Card index is out of bounds.");
    }
    int emptySpace = totalSpaces - totalFullSpaces;
    if (deck.size() + hand.size() - 1 < emptySpace) {
      throw new IllegalStateException("Cannot draw card: not enough cards in the deck.");
    }
    hand.remove(cardIdx);
    hand.add(deck.get(0));
    deck.remove(0);
  }

  @Override
  public void startGame(List deck, boolean shuffle, int handSize) {
    if (gameStart) {
      throw new IllegalStateException("Game has already been started.");
    }
    if (deck == null
        || deck.contains(null)
        || handSize < 1
        || deck.size() < handSize + totalSpaces) {
      throw new IllegalArgumentException("Invalid Deck");
    }
    if (handSize >= deck.size()) {
      throw new IllegalArgumentException("Invalid hand size.");
    }
    this.gameStart = true;
    this.deck.addAll(deck);
    if (shuffle) {
      Collections.shuffle(this.deck, random);
    }
    for (int i = 0; i < handSize; i++) {
      hand.add(this.deck.get(0));
      this.deck.remove(0);
    }
  }

  @Override
  public int getWidth() {
    return sideLength;
  }

  @Override
  public int getHeight() {
    return sideLength;
  }

  @Override
  public PokerCard getCardAt(int row, int col) {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started.");
    }
    if (isValidPosition(row, col)) {
      return board[row][col];
    } else {
      throw new IllegalArgumentException("Invalid location");
    }
  }

  @Override
  public List<PokerCard> getHand() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not started yet getHand.");
    }
    return hand;
  }

  @Override
  public int getRemainingDeckSize() {
    if (!gameStart) {
      throw new IllegalStateException("Game has not startedRemainingDeck");
    }
    return deck.size();
  }

  @Override
  public boolean isGameOver() {
    if (!gameStart) {
      throw new IllegalStateException("game has not started yet.");
    }
    if (totalSpaces == totalFullSpaces) {
      gameOver = true;
      return true;
    } else {
      gameOver = false;
      return false;
    }
  }

  /**
   * Determines if the position is valid according to the PokerTriangle rules.
   *
   * @param row the row index.
   * @param col the col index.
   * @return whether or not it was a valid position.
   */
  protected boolean isValidPosition(int row, int col) {
    return (row >= 0) && row < sideLength && col >= 0 && col <= row;
  }

  /**
   * Determines the factorial of a number in order to get the total number of squares.
   *
   * @param num the number.
   * @return the factorial of the number.
   */
  protected int getFactorial(int num) {
    int work = num;
    int result = 0;
    for (int i = 0; i < num; i++) {
      result += work;
      work--;
    }
    return result;
  }

  /**
   * Determines if the hand is a flush.
   *
   * <p>the provided hand.
   *
   * @return boolean if it is a flush.
   * @throws IllegalArgumentException hand size if it is not 5.
   */
  protected boolean isFlush(Map<Suit, List<Integer>> suitRanks) {
    for (Suit suit : suitRanks.keySet()) {
      if (suitRanks.get(suit).size() >= 5) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if a list is k of a kind.
   *
   * @param rankFreq the hand that will be compared.
   * @param k how many of a kind you want it to be (2, 3, 4).
   * @return whether it is k of a kind.
   */
  protected boolean isHowManyOfAKind(Map<Rank, Integer> rankFreq, int k) {
    for (Rank rank : rankFreq.keySet()) {
      if (rankFreq.get(rank) >= k) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determines if it is a full house.
   *
   * @param rankFreq hand that will be examined.
   * @return boolean if it is a full house.
   */
  protected boolean isFullHouse(Map<Rank, Integer> rankFreq) {
    boolean isPair = false;
    boolean isTrips = false;
    for (int val : rankFreq.values()) {
      if (val >= 3) {
        isTrips = true;
      } else if (val == 2) {
        isPair = true;
      }
    }
    return isPair && isTrips;
  }

  /**
   * Determines if the hand is a straight.
   *
   * @param rankFreq the hand that will be examined.
   * @return boolean if it is a straight.
   * @throws IllegalArgumentException if the handSize is not 5.
   */
  protected boolean isStraight(Map<Rank, Integer> rankFreq) {
    List<Integer> vals = new ArrayList<>();
    for (Rank rank : rankFreq.keySet()) {
      vals.add(rank.getval());
    }
    Collections.sort(vals);
    return isStraightInEachRank(vals);
  }

  /**
   * Determines if the hand is a straight flush.
   *
   * @param suitRanks the hand that is examined.
   * @return boolean if it is a straight flush.
   */
  protected boolean isStraightFlush(Map<Suit, List<Integer>> suitRanks) {
    for (Suit suit : suitRanks.keySet()) {
      List<Integer> sortedRank = suitRanks.get(suit);
      if (sortedRank.size() >= 5) {
        if (isStraightInEachRank(sortedRank)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Determines if there is a straight in the provided rank.
   *
   * @param sortedVals the list of number values that will be compared.
   * @return whether it is a straight.
   */
  protected boolean isStraightInEachRank(List<Integer> sortedVals) {
    List<Integer> uniqueCombos = new ArrayList<>();
    for (int val : sortedVals) {
      if (uniqueCombos.isEmpty() || !uniqueCombos.get(uniqueCombos.size() - 1).equals(val)) {
        uniqueCombos.add(val);
      }
    }
    if (uniqueCombos.size() < 5) {
      return false;
    }
    for (int start = 0; start <= uniqueCombos.size() - 5; start++) {
      boolean isStraight = true;
      for (int i = 0; i < 4; i++) {
        int curr = uniqueCombos.get(start + i);
        int next = uniqueCombos.get(start + i + 1);
        if (next - curr != 1) {
          isStraight = false;
          break;
        }
      }
      if (isStraight) {
        return true;
      }
    }
    return uniqueCombos.containsAll(Arrays.asList(2, 3, 4, 5, 14));
  }

  /**
   * Determines if the hand is 2 pair.
   *
   * @param map hand that is examined.
   * @return boolean if it is 2 pair.
   * @throws IllegalArgumentException if the hand size is not 5.
   */
  protected boolean isTwoPair(Map<Rank, Integer> map) {
    int pairs = 0;
    for (int count : map.values()) {
      if (count >= 2) {
        pairs++;
      }
      if (pairs >= 2) {
        return true;
      }
    }
    return false;
  }

  /**
   * Helper method to build a frequency map to compare the 2 pair hands.
   *
   * @param hand the hand that a freq map will be build of.
   * @return the freq map of the hand.
   */
  protected Map<Rank, Integer> buildFrequencyMap(List<PokerCard> hand) {
    Map<Rank, Integer> freq = new HashMap<>();
    for (PokerCard card : hand) {
      Rank rank = card.getRank();
      freq.put(rank, freq.getOrDefault(rank, 0) + 1);
    }
    return freq;
  }

  /**
   * Gets the list of hands and determines the highest rank of it.
   *
   * @param cards the hand that will be evaluated
   * @return the point value of the best hand.
   */
  private int evaluateBestHand(List<PokerCard> cards) {
    if (cards.size() < 5) {
      return 0;
    }
    Map<Rank, Integer> rankFreq = new HashMap<>();
    Map<Suit, List<Integer>> suitRanks = new HashMap<>();
    for (PokerCard card : cards) {
      rankFreq.put(card.getRank(), rankFreq.getOrDefault(card.getRank(), 0) + 1);
      suitRanks.putIfAbsent(card.getSuit(), new ArrayList<>());
      suitRanks.get(card.getSuit()).add(card.getRank().getval());
    }

    for (Suit suit : suitRanks.keySet()) {
      Collections.sort(suitRanks.get(suit));
    }
    int bestScore = 0;
    if (isStraightFlush(suitRanks)) {
      return 75;
    }
    if (isHowManyOfAKind(rankFreq, 4)) {
      bestScore = Math.max(bestScore, 50);
    }
    if (isFullHouse(rankFreq)) {
      bestScore = Math.max(bestScore, 25);
    }
    if (isFlush(suitRanks)) {
      bestScore = Math.max(bestScore, 20);
    }
    if (isStraight(rankFreq)) {
      bestScore = Math.max(bestScore, 15);
    }
    if (isHowManyOfAKind(rankFreq, 3)) {
      bestScore = Math.max(bestScore, 10);
    }
    if (isTwoPair(rankFreq)) {
      bestScore = Math.max(bestScore, 5);
    }
    if (isHowManyOfAKind(rankFreq, 2)) {
      bestScore = Math.max(bestScore, 2);
    }
    return bestScore;
  }
}
