package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.util.List;

/**
 * A mock implementation of PokerPolygons that can be used in controller tests.
 * By default, it logs calls to each method in an internal StringBuilder. Tests can override
 * specific methods inline (e.g., to throw exceptions) if needed.
 */
public class MockPokerPolygon implements PokerPolygons<Card> {

  private final StringBuilder log;

  /**
   * Constructs a default mock. The log stores any method calls; tests can check or ignore it.
   */
  public MockPokerPolygon() {
    this.log = new StringBuilder();
  }

  /**
   * Returns the cumulative log of all method calls made so far.
   */
  public String getLog() {
    return log.toString();
  }

  @Override
  public void placeCardInPosition(int cardIdx, int row, int col) {
    log.append(String.format("placeCardInPosition(%d, %d, %d)\n", cardIdx, row, col));
  }

  @Override
  public void discardCard(int cardIdx) {
    log.append(String.format("discardCard(%d)\n", cardIdx));
  }

  @Override
  public void startGame(List deck, boolean shuffle, int handSize) {
    log.append(String.format("startGame(deckSize=%d, shuffle=%b, handSize=%d)\n",
        (deck == null ? 0 : deck.size()), shuffle, handSize));
  }

  @Override
  public int getWidth() {
    log.append("getWidth()\n");
    return 0;
  }

  @Override
  public int getHeight() {
    log.append("getHeight()\n");
    return 0;
  }

  @Override
  public List<Card> getNewDeck() {
    log.append("getNewDeck()\n");
    return null;
  }

  @Override
  public Card getCardAt(int row, int col) {
    log.append(String.format("getCardAt(%d, %d)\n", row, col));
    return null;
  }

  @Override
  public List<Card> getHand() {
    log.append("getHand()\n");
    return null;
  }

  @Override
  public int getScore() {
    log.append("getScore()\n");
    return 0;
  }

  @Override
  public int getRemainingDeckSize() {
    log.append("getRemainingDeckSize()\n");
    return 0;
  }

  @Override
  public boolean isGameOver() {
    log.append("isGameOver()\n");
    return false;
  }
}
