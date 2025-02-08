package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;

/**
 * A JUnit test class for PokerPolygonsTextualController,
 * now using both MockPokerPolygon (model) and MockPokerTrianglesTextualView (view).
 */
public class TestController {

  /**
   * Creates a new PokerPolygonsTextualController with the given input,
   * then calls playGame() with the provided model, view, deck, etc.
   *
   * @param input    the user commands (String)
   * @param model    the mock model
   * @param view     the mock textual view
   * @param deck     a list of cards for the game (could be null or empty)
   * @param shuffle  whether to shuffle
   * @param handSize how many cards to deal
   * @return the final string output from the controller's Appendable
   */
  private <C extends Card> String runControllerWithInput(
      String input,
      PokerPolygons<C> model,
      PokerPolygonsTextualView view,
      List<C> deck,
      boolean shuffle,
      int handSize) {
    Readable rd = new StringReader(input);
    Appendable ap = new StringWriter();
    PokerPolygonsTextualController controller = new PokerPolygonsTextualController(rd, ap);
    try {
      controller.playGame(model, view, deck, shuffle, handSize);
    } catch (IllegalStateException e) {
      // Unnecessary cuz we won't be giving invalid.
    }
    return ap.toString();
  }

  @Test
  public void testControllerGettingBadLetterAsCommand() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);

    String input = "X 1 1 1\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(output.toLowerCase().contains("invalid move"));
    Assert.assertFalse(mockModel.getLog().contains("placeCardInPosition"));
    Assert.assertTrue(mockView.getViewLog().contains("toString() called"));
  }

  @Test
  public void testIntegrationWithStudentComponentsByPlayingGame() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1 1\ndiscard 1\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 0, 0)"));
    Assert.assertTrue(mockModel.getLog().contains("discardCard(0)"));
    Assert.assertTrue(mockView.getViewLog().split("toString\\(\\) called").length > 1);
    Assert.assertTrue(output.contains("Score"));
  }

  @Test
  public void testEmptyReadable() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);

    String input = "";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("startGame"));
    Assert.assertFalse(output.isEmpty());
    Assert.assertTrue(mockView.getViewLog().contains("toString() called"));
  }

  @Test
  public void testNoCardsInDeckGivenToPlayGame() {
    PokerPolygons<Card> mockModel = new MockPokerPolygon() {
      @Override
      public List<Card> getNewDeck() {
        return Collections.emptyList();
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1 1\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, mockModel.getNewDeck(),
        false, 5);

    if (output.toLowerCase().contains("could not start game")) {
      Assert.assertTrue(true);
    } else {
      Assert.assertTrue(output.toLowerCase().contains("score")
          || output.toLowerCase().contains("invalid"));
    }
  }

  @Test
  public void testDiscardWithValidNumbersButUnableToDiscard() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void discardCard(int cardIdx) {
        super.discardCard(cardIdx);
        throw new IllegalArgumentException("Unable to discard");
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "discard 4\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("discardCard(3)"));
    Assert.assertTrue(output.toLowerCase().contains("unable to discard")
        || output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testControllerBehaviorWithNullModel() {
    // We'll create the controller, but pass null for model
    MockPokerPolygon someModel = new MockPokerPolygon(); // not used
    MockPokerTrianglesTextualView view = new MockPokerTrianglesTextualView(someModel);
    PokerPolygonsTextualController c =
        new PokerPolygonsTextualController(new StringReader(""), new StringWriter());
    Assert.assertThrows(IllegalArgumentException.class,
        () -> c.playGame(null, view, null, false, 5));
  }

  @Test
  public void testEmptyAppendable() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 2 3\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertFalse(output.isEmpty());
    Assert.assertTrue(mockView.getViewLog().contains("toString() called"));
  }

  @Test
  public void testDiscardWithValidArgs() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);

    String input = "discard 3\ndiscard 5\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);

    Assert.assertTrue(mockModel.getLog().contains("discardCard(2)"));
    Assert.assertTrue(mockModel.getLog().contains("discardCard(4)"));
    Assert.assertTrue(output.toLowerCase().contains("score"));
  }

  @Test
  public void testPlaceCardInPositionWithValidNumbersButOccupiedPosition() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void placeCardInPosition(int cardIdx, int row, int col) {
        super.placeCardInPosition(cardIdx, row, col);
        throw new IllegalArgumentException("Position occupied");
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 2 3\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 1, 2)"));
    Assert.assertTrue(output.toLowerCase().contains("position occupied")
        || output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testIntegrationWithStudentComponentsByImmediatelyQuitting() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "q\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("startGame"));
    Assert.assertFalse(mockModel.getLog().contains("placeCardInPosition"));
    Assert.assertFalse(mockModel.getLog().contains("discardCard"));
    Assert.assertTrue(output.toLowerCase().contains("game quit"));
  }

  @Test
  public void testPlaceCardInPositionWithValidNumbersButColIdxOutOfBounds() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void placeCardInPosition(int cardIdx, int row, int col) {
        super.placeCardInPosition(cardIdx, row, col);
        if (col < 0 || col > 5) {
          throw new IllegalArgumentException("Column out of bounds");
        }
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 2 2 99\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(1, 1, 98)"));
    Assert.assertTrue(output.toLowerCase().contains("out of bounds")
        || output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testDiscardWithValidNumbersOutOfBounds() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void discardCard(int cardIdx) {
        super.discardCard(cardIdx);
        if (cardIdx < 0 || cardIdx > 51) {
          throw new IllegalArgumentException("Card index out of bounds");
        }
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "discard 999\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("discardCard(998)"));
    Assert.assertTrue(output.toLowerCase().contains("out of bounds") ||
        output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testPlaceCardInPositionWithValidNumbersButRowOutOfBounds() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void placeCardInPosition(int cardIdx, int row, int col) {
        super.placeCardInPosition(cardIdx, row, col);
        if (row < 0 || row > 5) {
          throw new IllegalArgumentException("Row out of bounds");
        }
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 999 0\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 998, -1)"));
    Assert.assertTrue(output.toLowerCase().contains("out of bounds") ||
        output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testPlaceCardInPositionWithModelInvalidMoveContinuesToReadInput() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      private boolean firstCall = true;
      @Override
      public void placeCardInPosition(int cardIdx, int row, int col) {
        super.placeCardInPosition(cardIdx, row, col);
        if (firstCall) {
          firstCall = false;
          throw new IllegalArgumentException("Invalid placement");
        }
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1 1\nplace 2 2 2\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 0, 0)"));
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(1, 1, 1)"));
    Assert.assertTrue(output.toLowerCase().contains("invalid placement")
        || output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testControllerQuittingAtStartAndCaseInsensitive() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "Q\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertFalse(mockModel.getLog().contains("placeCardInPosition"));
    Assert.assertFalse(mockModel.getLog().contains("discardCard"));
    Assert.assertTrue(output.toLowerCase().contains("game quit"));
  }

  @Test
  public void testPlaceCommandWithValidArgs() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1 1\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 0, 0)"));
    Assert.assertTrue(output.toLowerCase().contains("score"));
  }

  @Test
  public void testPlaceCardInPositionWithValidNumbersButCardIdxOutOfBounds() {
    MockPokerPolygon mockModel = new MockPokerPolygon() {
      @Override
      public void placeCardInPosition(int cardIdx, int row, int col) {
        super.placeCardInPosition(cardIdx, row, col);
        if (cardIdx < 0 || cardIdx > 51) {
          throw new IllegalArgumentException("Card index out of bounds");
        }
      }
    };
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 999 0 0\nq\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(998, -1, -1)"));
    Assert.assertTrue(output.toLowerCase().contains("out of bounds")
        || output.toLowerCase().contains("invalid move"));
  }

  @Test
  public void testRunOutOfInput() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertNotNull(output);
    Assert.assertFalse(mockModel.getLog().contains("placeCardInPosition(0, 0,"));
  }

  @Test
  public void testQuittingAtAnyPointWhenEnteringAnyCommand() {
    MockPokerPolygon mockModel = new MockPokerPolygon();
    MockPokerTrianglesTextualView mockView = new MockPokerTrianglesTextualView(mockModel);
    String input = "place 1 1 1\ndiscard 3\nq\nplace 4 4 4\n";
    String output = runControllerWithInput(input, mockModel, mockView, null, false, 5);
    Assert.assertTrue(mockModel.getLog().contains("placeCardInPosition(0, 0, 0)"));
    Assert.assertTrue(mockModel.getLog().contains("discardCard(2)"));
    Assert.assertFalse(mockModel.getLog().contains("placeCardInPosition(3, 3, 3)"));
    Assert.assertTrue(output.toLowerCase().contains("game quit"));
  }
}
