// start
package poad.G.bits;

import java.util.Random;

import poad.IUnarySearchOperation;

/** a unary search operation that randomly flipping a single bit in a bit string */
// end
public class BitsUnarySingleFlip implements IUnarySearchOperation<boolean[]> {// start
  /** instantiate */
  public BitsUnarySingleFlip() {
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

    g = p.clone(); // copy parent string
    g[r.nextInt(g.length)] ^= true; // flip the bit
    return g; // return new bit string
  }
}
