package cs3500.pokerpolygons.view;

import java.io.IOException;

/**
 * Interface for a view for the Poker Polygons game. The interface does not assume the shape of the
 * polygonal board, so it is up to the implementation to describe exactly how the board is printed.
 */
public interface PokerPolygonsTextualView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   * to the given appendable.
   * @param out where to send the model information to
   * @throws IOException if the rendering fails for some reason
   */
  void render(Appendable out) throws IOException;

  String toString();
}
