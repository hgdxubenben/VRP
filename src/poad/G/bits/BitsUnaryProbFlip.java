// start
package poad.G.bits;

import java.util.Random;

import poad.IUnarySearchOperation;
import project.examples.TabuList;

/** a unary search operation that randomly flipping bits in a bit string */
// end
public class BitsUnaryProbFlip implements IUnarySearchOperation<boolean[]> {// start

  /** the flipping probability */
  public final double prob;

  /**
   * instantiate
   * 
   * @param p
   *          the flipping probability
   */
  public BitsUnaryProbFlip(final double p) {
    super();
    this.prob = p;
  }

  /**
   * Mutate a bit string!
   * 
   * @param p
   *          the existing parent bit string
   * @param r
   *          the randomizer
   */
  @Override
  // end
  public boolean[] mutate(final boolean[] p, final Random r) {
    final boolean[] g;
    int i;

    g = p.clone(); // copy parent string
    for (i = g.length; (--i) >= 0;) {// go through the bits
      if (r.nextDouble() < this.prob) { // flip each bit with the same probability
        g[i] = (!(g[i]));// flip
      }
    }

    return g; // return new bit string
  }

  @Override
  public boolean[] mutate(boolean[] parent, TabuList tabuList) {
    // TODO Auto-generated method stub
    return null;
  }
}
