// start
package poad.G.bits;

import java.util.Random;

import poad.IBinarySearchOperation;

/** a binary single-point crossover for bit strings */
// end
public class BitsBinarySPX implements IBinarySearchOperation<boolean[]> {// start
  /** instantiate */
  public BitsBinarySPX() {
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
