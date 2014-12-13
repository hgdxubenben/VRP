// start
package poad.examples.Rn;

import poad.IObjectiveFunction;
import poad.G.Rn.Rn;

// end

/** the sphere function */
public class Sphere extends Rn implements IObjectiveFunction<double[]> {// start
  /**
   * instantiate
   * 
   * @param rn
   *          the dimension info
   */
  public Sphere(final Rn rn) {
    super(rn);
  }

  /** {@inheritDoc} */
  // end
  @Override
  public final double compute(final double[] x) {
    double s = 0d;

    for (int i = this.dim; (--i) >= 0;) {
      s += (x[i] * x[i]);
    }

    return s;
  }
}
