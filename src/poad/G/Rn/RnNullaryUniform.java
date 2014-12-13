// start
package poad.G.Rn;

import java.util.Random;

import poad.INullarySearchOperation;

/** a nullary search operation uniformly sampling the Rn */
// end
public final class RnNullaryUniform extends Rn implements INullarySearchOperation<double[]> {// start
  /**
   * instantiate this operation for a given subspace of the Rn
   * 
   * @param def
   *          the subspace
   */
  public RnNullaryUniform(final Rn def) {
    super(def);
  }

  /** {@inheritDoc} */
  // end
  @Override
  public final double[] create(final Random r) {
    final double[] g = new double[this.dim];

    for (int i = g.length; (--i) >= 0;) {
      g[i] = (this.min + (r.nextDouble() * (this.max - this.min)));
    }

    return g;
  }
}
