// start
package poad.G.Rn;

import java.util.Random;

import poad.IUnarySearchOperation;
import project.examples.TabuList;

/** a unary search operation that copies and modifies an existing vector */
// end
public final class RnUnaryNormal extends Rn implements IUnarySearchOperation<double[]> { // start
  /**
   * instantiate this operation for a given subspace of the Rn
   * 
   * @param def
   *          the subspace
   */
  public RnUnaryNormal(final Rn def) {
    super(def);
  }

  /** {@inheritDoc} */
  // end
  @Override
  public final double[] mutate(final double[] genotype, final Random r) {
    double d;

    final double[] g = genotype.clone();
    final int      i = r.nextInt(g.length);

    do {
      d = (g[i] + (r.nextGaussian() * (this.max - this.min) * 0.01d));
    } while ((d < this.min) || (d > this.max));

    g[i] = d;
    return g;
  }

  @Override
  public double[] mutate(double[] parent, TabuList tabuList) {
    // TODO Auto-generated method stub
    return null;
  }
}
