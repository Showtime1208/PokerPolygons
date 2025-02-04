package cs3500.pokerpolygons;

import cs3500.pokerpolygons.controller.PokerPolygonsTextualController;
import cs3500.pokerpolygons.model.hw02.PokerTriangles;
import cs3500.pokerpolygons.view.PokerTrianglesTextualView;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    PokerTriangles model = new PokerTriangles(7, new Random(100));
    PokerTrianglesTextualView view = new PokerTrianglesTextualView(model);
    new PokerPolygonsTextualController(new InputStreamReader(System.in), System.out).playGame(model, view, model.getNewDeck(), true, 5);
  }
}
