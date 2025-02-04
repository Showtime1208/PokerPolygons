package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.Card;
import cs3500.pokerpolygons.model.hw02.PokerPolygons;
import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.module.ResolutionException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PokerPolygonsTextualController implements PokerPolygonsController {
  private Scanner scan;
  private Appendable out;
  private PokerPolygons model;
  private PokerPolygonsTextualView view;

  public PokerPolygonsTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.scan = new Scanner(rd);
    this.out = ap;
  }

  @Override
  public <C extends Card> void playGame(
      PokerPolygons<C> model,
      PokerPolygonsTextualView view,
      List<C> deck,
      boolean shuffle,
      int handSize) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model or deck is null");
    }
    this.model = model;
    this.view = view;
    model.startGame(deck, shuffle, handSize);
    try {
      while (!model.isGameOver()) {
        printGameState();
        printScore();
        handleUserInput();
        if (model.isGameOver()) {
          printGameOverMessage();
          return;
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("IO Exception shawty.");
    }
  }

  private void transmit(String message) throws IOException {
    out.append(message).append("\n");
  }

  private void printGameState() throws IOException {
    transmit(view.toString());
  }

  private void printScore() throws IOException {
    transmit("Score: " + model.getScore());
  }

  private void printGameOverMessage() throws IOException {
    printGameState();
    transmit("Game over. Score: " + model.getScore());
  }

  private void printQuitGameMessage() throws IOException {
    transmit("Game quit!");
    transmit("State of the game when quit:");
    printGameState();
    printScore();
  }

  private int getNextInt() throws FileNotFoundException {
    try {
      return scan.nextInt();
    } catch (InputMismatchException ex) {
      String input = scan.next();
      if (input.equalsIgnoreCase("q")) {
        throw new FileNotFoundException("File");
      } else {
        return getNextInt();
      }
    }
  }

  private void handleUserInput2() throws IOException {
    String input = scan.next();
    try {
      if (input.equalsIgnoreCase("q")) {
        printQuitGameMessage();
        return;
      } else if (!input.equals("place") && !input.equals("discard")) {
        transmit("Invalid move. Play again. Expected: place, discard Actual: " + input);
      } else if (input.equals("place")) {
        int cardIdx = getNextInt()-1;
        int row = getNextInt()-1;
        int col = getNextInt()-1;
        model.placeCardInPosition(cardIdx, row, col);
      } else {
        int cardIdx = getNextInt();
        model.discardCard(cardIdx);
      }
    } catch (FileNotFoundException ex) {
      printQuitGameMessage();
    } catch (IllegalStateException exception) {
      transmit("Invalid move. Play");
      handleUserInput();
    }
  }
  private int handleUserCommandInput() {
    String input = scan.next();
    if (input.equalsIgnoreCase("q")) {
      return -1;
    }
    if (input.equalsIgnoreCase("place")) {
      return 0;
    }
    if (input.equalsIgnoreCase("discard")) {
      return 1;
    } else return 2;
  }

  private void handleUserInput() throws IOException {
    String input = scan.next();
    switch (input.toLowerCase()) {
      case "place":
      }
    }
}
