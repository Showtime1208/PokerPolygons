package cs3500.pokerpolygons.controller;

import cs3500.pokerpolygons.model.hw02.PokerPolygons;

import cs3500.pokerpolygons.view.PokerPolygonsTextualView;
import java.io.IOException;

/**
 * A mock version of PokerTrianglesTextualView for testing purposes.
 * It logs calls to render() and toString() in an internal log.
 */
public class MockPokerTrianglesTextualView implements PokerPolygonsTextualView {

  private final StringBuilder viewLog;

  /**
   * Constructor for the mock view class.
   * @param model the model to be "viewed" (though we only log calls here)
   */
  public MockPokerTrianglesTextualView(PokerPolygons<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid View initialization.");
    }
    this.viewLog = new StringBuilder();
  }

  @Override
  public String toString() {
    viewLog.append("toString() called\n");
    return "Mocked View State";
  }

  @Override
  public void render(Appendable out) throws IOException {
    viewLog.append("render(...) called\n");
    out.append("Mocked View State\n");
  }

  /**
   * Retrieves the log of calls made to this mock view.
   * @return the current contents of the view log
   */
  public String getViewLog() {
    return viewLog.toString();
  }
}

