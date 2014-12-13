// start
package poad.algorithms.ea.selection;

import java.util.Random;

import poad.Individual;
import poad.algorithms.ea.ISelectionAlgorithm;

/** the tournament selection algorithm */
// end
public class TournamentSelection implements ISelectionAlgorithm { // start
  /** the k */
  public final int k;

  /**
   * the tournament selection
   * 
   * @param s
   *          the tournament size
   */
  public TournamentSelection(final int s) {
    super();
    this.k = s;
  }

  /** {@inheritDoc} */
  @Override
  // end
  public void select(final Individual<?, ?>[] pop, final Individual<?, ?>[] mate, final Random r) {
    /** TODO */
  }
}
