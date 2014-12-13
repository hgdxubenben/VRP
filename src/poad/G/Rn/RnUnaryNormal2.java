// start
package poad.G.Rn;

import java.util.Random;

import poad.IUnarySearchOperation;

/** a unary search operation that copies and modifies an existing vector */
// end
public class RnUnaryNormal2 extends Rn implements IUnarySearchOperation<double[]> { // start
  /**
   * instantiate this operation for a given subspace of the Rn
   * 
   * @param def
   *          the subspace
   */
  public RnUnaryNormal2(final Rn def) {
    super(def);
  }

  /** {@inheritDoc} */
  // end
  @Override
  public double[] mutate(final double[] genotype, final Random r) {
    double d;

    final double[] g = genotype.clone();

    for (int i = g.length; (--i) >= 0;) {
      do {
        d = (g[i] + (r.nextGaussian() * (this.max - this.min) * 0.01d));
      } while ((d < this.min) || (d > this.max));
      g[i] = d;
    }

    return g;
  }
}
