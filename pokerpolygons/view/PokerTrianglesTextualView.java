package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import java.io.IOException;
import java.util.List;

/** View implementation of the PokerPolygonsTextualView for the PokerTriangles Class. */
public class PokerTrianglesTextualView implements PokerPolygonsTextualView {
  private final PokerPolygons<?> model;

  /**
   * Constructor for the PokerTrianglesTextualView.
   *
   * @param model the PokerPolygons model that will be viewed.
   */
  public PokerTrianglesTextualView(PokerPolygons<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View initializationn.");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int side = model.getHeight();
    for (int row = 0; row < side; row++) {
      for (int col = 0; col <= row; col++) {
        Card card = model.getCardAt(row, col);
        String cardString = (card == null) ? " __" : card.toString();
        if (cardString.length() == 2) {
          cardString = " " + cardString;
        }
        if (col == 0) {
          sb.append(cardString);
        } else {
          sb.append(" ").append(cardString);
        }
      }
      sb.append("\n");
    }

    sb.append("Deck: ").append(model.getRemainingDeckSize()).append("\n");
    sb.append("Hand: ");
    List<Card> hand = (List<Card>) model.getHand();
    if (hand.isEmpty()) {
      /*This is needed because I want it to only do the following things if the hand isn't empty
      but if the hand is empty I want nothing to happen.
       */
    } else {
      for (int i = 0; i < hand.size(); i++) {
        if (i > 0) {
          sb.append(", ");
        }
        sb.append(hand.get(i).toString());
      }
    }
    return sb.toString();
  }

  @Override
  public void render(Appendable out) throws IOException {
     out.append(this.toString());
  }
}
