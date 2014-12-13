// start
package poad.G.permutations;

import java.util.Random;

import poad.IUnarySearchOperation;

/** a unary search operation that randomly swapping elements in a permutation */
// end
public class PermutationUnarySwap implements IUnarySearchOperation<int[]> {// start
  /** instantiate */
  public PermutationUnarySwap() {
    super();
  }

  /**
   * Create a permutation!
   * 
   * @param p
   *          the existing parent permutation
   * @param r
   *          the randomizer
   */
  // end
  @Override
  public int[] mutate(final int[] p, final Random r) {
    int i, j, t;

    final int[] g = p.clone();
    do {
      i = r.nextInt(g.length);
      do {
        j = r.nextInt(g.length);
      } while (j == i);
      t    = g[i];
      g[i] = g[j];
      g[j] = t;
    } while (r.nextBoolean());

    return g; // return new permutation
  }
}
