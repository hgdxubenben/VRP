// start
package poad.G.permutations;

import java.util.Random;

import poad.IUnarySearchOperation;
import project.examples.TabuList;

/** a unary search operation that randomly swapping elements in a permutation */
// end
public class PermutationUnarySingleSwap implements IUnarySearchOperation<int[]> {// start
  /** instantiate */
  public PermutationUnarySingleSwap() {
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
    final int[] g;
    int i, j, t;

    g = p.clone();
    i = r.nextInt(g.length);
    do {
      j = r.nextInt(g.length);
    } while (j == i);
    t    = g[i];
    g[i] = g[j];
    g[j] = t;

    return g; // return new permutation
  }

  @Override
  public int[] mutate(int[] parent, TabuList tabuList) {
    // TODO Auto-generated method stub
    return null;
  }
}
