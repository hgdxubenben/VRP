// start
package poad.algorithms.ea.selection;

import java.util.Random;

import poad.Individual;
import poad.algorithms.ea.ISelectionAlgorithm;

/** the truncation selection algorithm */
// end
public class TruncationSelection implements ISelectionAlgorithm { // start

  /** the globally shared instance */
  public static final TruncationSelection INSTANCE = new TruncationSelection();

  /** the truncation selection */
  private TruncationSelection() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  // end
  public void select(final Individual<?, ?>[] pop, final Individual<?, ?>[] mate, final Random r) {
    /** TODO */
  }
}
