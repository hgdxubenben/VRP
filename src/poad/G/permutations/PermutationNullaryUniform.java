// start
package poad.G.permutations;

import java.util.Random;

import poad.INullarySearchOperation;

/** a nullary search operation that creates a permutation of the numbers 0..n-1 */
// end
public class PermutationNullaryUniform implements INullarySearchOperation<int[]> {
  /** the length of the permutations */
  public final int n; // start

  /**
   * instantiate
   * 
   * @param m
   *          the number of elements to permutation
   */
  public PermutationNullaryUniform(final int m) {
    super();
    this.n = m;
  }

  /**
   * Create a permutation!
   * 
   * @param r
   *          the randomizer
   */
  // end
  @Override
  public int[] create(final Random r) {
    int i, j, t;

    final int[] g = new int[this.n];

    for (i = this.n; (--i) >= 0;) {
      g[i] = i;
    }

    for (i = 0; i<this.n; i++) {
      j    = (i + r.nextInt(this.n - i)); // (*@\dsoldcitep{FY1938STFBAAMR,D1964A235RP,K1969TAOCPSNA}@*)
      t    = g[j];
      g[j] = g[i];
      g[i] = t;
    }
    return g;
  }
}
