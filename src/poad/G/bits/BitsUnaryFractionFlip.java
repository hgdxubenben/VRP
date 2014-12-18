// start
package poad.G.bits;

import java.util.Random;

import poad.IUnarySearchOperation;


/** a unary search operation that randomly flipping a certain fractions of the bits in a bit string */
// end
public class BitsUnaryFractionFlip implements IUnarySearchOperation<boolean[]> {
  /** the fraction to flip */
  public final double frac;// start

  /**
   * instantiate
   * 
   * @param f
   *          the flipping fraction
   */
  public BitsUnaryFractionFlip(final double f) {
    super();
    this.frac = f;
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
    int f;

    g = p.clone(); // copy parent string
    f = Math.max(1, Math.min(p.length, ((int) (p.length * this.frac))));
    for (; (--f) >= 0;) {// go through the bits
      g[r.nextInt(p.length)] ^= true; // flip
    }

    return g; // return new bit string
  }

  @Override
  public boolean[] mutate(boolean[] parent) {
    // TODO Auto-generated method stub
    return null;
  }
}
