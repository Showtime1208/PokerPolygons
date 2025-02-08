package cs3500.pokerpolygons.view;

import cs3500.pokerpolygons.model.hw02.PokerCard;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/** Test Class for the PokerTrianglesTextView class. */
public class TestView {
  @Test
  public void testTextViewConstructorInvalidNullModel() {
    Assert.assertThrows(IllegalArgumentException.class, () -> new PokerTrianglesTextualView(null));
  }

  @Test
  public void testTextViewToStringValid5CardsSixLength() {
    String string =
        " __\n"
            + " __  __\n"
            + " __  __  __\n"
            + " __  __  __  __\n"
            + " __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + "Deck: 47\n"
            + "Hand: 2♡, 2♠, 2♢, 2♣, 3♡";
    PokerTriangles model = new PokerTriangles(6);
    List<PokerCard> deck = new ArrayList<>(model.getNewDeck());
    model.startGame(deck, false, 5);

    Assert.assertEquals(string, new PokerTrianglesTextualView(model).toString());
  }

  @Test
  public void testTextViewToStringValid6Cards5Length() {
    String string =
        " __\n"
            + " __  __\n"
            + " __  __  __\n"
            + " __  __  __  __\n"
            + " __  __  __  __  __\n"
            + "Deck: 46\n"
            + "Hand: 2♡, 2♠, 2♢, 2♣, 3♡, 3♠";
    PokerTriangles model = new PokerTriangles(5);
    List<PokerCard> deck = new ArrayList<>(model.getNewDeck());
    model.startGame(deck, false, 6);
    Assert.assertEquals(string, new PokerTrianglesTextualView(model).toString());
  }

  @Test
  public void testTextViewToStringValid7Cards7Length() {
    String string =
        " __\n"
            + " __  __\n"
            + " __  __  __\n"
            + " __  __  __  __\n"
            + " __  __  __  __  __\n"
            + " __  __  __  __  __  __\n"
            + " __  __  __  __  __  __  __\n"
            + "Deck: 45\n"
            + "Hand: 2♡, 2♠, 2♢, 2♣, 3♡, 3♠, 3♢";
    PokerTriangles model = new PokerTriangles(7);
    List<PokerCard> deck = new ArrayList<>(model.getNewDeck());
    model.startGame(deck, false, 7);
    Assert.assertEquals(string, new PokerTrianglesTextualView(model).toString());
  }
}
