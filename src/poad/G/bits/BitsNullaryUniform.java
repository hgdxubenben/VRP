// start
package poad.G.bits;

import java.util.Random;

import poad.INullarySearchOperation;

/** a nullary search operation that creates a bit string */
// end
public class BitsNullaryUniform implements INullarySearchOperation<boolean[]> {
  /** the length of the bit string */
  public final int n; // start

  /**
   * instantiate
   * 
   * @param m
   *          the number of elements
   */
  public BitsNullaryUniform(final int m) {
    super();
    this.n = m;
  }

  /**
   * Create a bit string!
   * 
   * @param r
   *          the randomizer
   */
  @Override
  // end
  public boolean[] create(final Random r) {
    final boolean[] g;
    int i;

    g = new boolean[this.n];         // allocate integer array
    for (i = this.n; (--i) >= 0;) { // initialize each element to its index
      if (r.nextBoolean()) {
        g[i] = true;
      }
    }
    return g;
  }
}
