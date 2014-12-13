// start
package poad.algorithms.ea.selection;

import java.util.Arrays;
import java.util.Random;

import poad.Individual;
import poad.algorithms.ea.ISelectionAlgorithm;

/** the roulette wheel selection algorithm */
// end
public class RouletteWheelSelection implements ISelectionAlgorithm { // start
  /** the temp */
  private double[] temp;

  /** the roulette wheel selection */
  public RouletteWheelSelection() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  // end
  public void select(final Individual<?, ?>[] pop, final Individual<?, ?>[] mate, final Random r) {
    double[] t;
    double max, last;
    int i, j;

    t = this.temp;
    if ((t == null) || (t.length < pop.length)) {
      this.temp = t = new double[pop.length];
    }

    max = Double.NEGATIVE_INFINITY;
    for (Individual<?, ?> indi : pop) {
      max = Math.max(indi.v, max);
    }

    max = Math.nextUp(max);
    last = 0d;
    for (i = 0; i < t.length; i++) {
      last += (max - pop[i].v);
      t[i] = last;
    }
    t[t.length - 1] = Double.POSITIVE_INFINITY;

    for (i = 0; i < mate.length; i++) {
      j = Arrays.binarySearch(t, last * r.nextDouble());
      if (j < 0) {
        j = ((-j) - 1);
      }
      mate[i] = pop[j];
    }
  }
}
