// start
package poad.examples.Rn;

import poad.IObjectiveFunction;
import poad.G.Rn.Rn;

// end

/** Schwefel problem 2.21 */
public class Schwefel_2_21 extends Rn implements IObjectiveFunction<double[]> {// start
  /**
   * instantiate
   * 
   * @param rn
   *          the dimension info
   */
  public Schwefel_2_21(final Rn rn) {
    super(rn);
  }

  /** {@inheritDoc} */
  // end
  @Override
  public final double compute(final double[] x) {
    double m = 0d;

    for (int i = this.dim; (--i) >= 0;) {
      final double d = Math.abs(x[i]);
      if (d > m) {
        m = d;
      }
    }

    return m;
  }
}
