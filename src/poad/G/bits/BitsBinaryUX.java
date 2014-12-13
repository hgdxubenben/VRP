// start
package poad.G.bits;

import java.util.Random;

import poad.IBinarySearchOperation;

/** a binary uniform crossover for bit strings */
// end
public class BitsBinaryUX implements IBinarySearchOperation<boolean[]> {// start
  /** instantiate */
  public BitsBinaryUX() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  // end
  public boolean[] recombine(final boolean[] p1, final boolean[] p2, final Random r) {
    return p2;
    /** TODO */
  }
}
