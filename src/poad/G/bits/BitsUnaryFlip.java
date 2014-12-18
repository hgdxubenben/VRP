// start
package poad.G.bits;

import java.util.Random;

import poad.IUnarySearchOperation;

/** a unary search operation that is randomly flipping bits in a bit string */
// end
public class BitsUnaryFlip implements IUnarySearchOperation<boolean[]> {// start
  /** instantiate */
  public BitsUnaryFlip() {
    super();
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
    
    g = p.clone();                    // copy parent string
    do {                              // at least once, but maybe more often
      g[r.nextInt(g.length)] ^= true; // flip the bit
    } while (r.nextBoolean());        // maybe repeat (small chance for large changes
    return g;                         // return new bit string
  }

  @Override
  public boolean[] mutate(boolean[] parent) {
    // TODO Auto-generated method stub
    return null;
  }
}
