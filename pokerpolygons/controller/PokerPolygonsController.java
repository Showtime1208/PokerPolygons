package cs3500.pokerpolygons.controller;

import java.util.List;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;

/**
 * Driver of the Poker Polygons game. Reads user input to determine what move
 * the player should make and prints the current game state after each command.
 */
public interface PokerPolygonsController {

  /**
   * Starts and plays the game using user input from the player.
   * @param model state of the game
   * @param view view for the model
   * @param deck the deck of cards used to play the game
   * @param shuffle true iff the deck should be shuffled.
   * @param handSize the starting hand size
   * @param <C> the type of the Card used in the model
   * @throws IllegalStateException if controller is unable to successfully receive input,
   *     transmit output, or if the game cannot be started
   * @throws IllegalArgumentException if the model or view are null
   */
  <C extends Card> void playGame(PokerPolygons<C> model, PokerPolygonsTextualView view,
                                 List<C> deck, boolean shuffle, int handSize);

}
