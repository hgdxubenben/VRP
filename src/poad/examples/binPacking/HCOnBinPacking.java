// start
package poad.examples.binPacking;

import poad.Individual;
import poad.G.permutations.PermutationNullaryUniform;
import poad.G.permutations.PermutationUnarySwap;
import poad.algorithms.HC;
import poad.termination.MaxSteps;

/** a simple test class applying the hill climber to the bin packing problem */
// end
public class HCOnBinPacking {// start
  /**
   * Run the test class
   * 
   * @param args
   *          the command line arguments
   */
  // end
  public static void main(final String[] args) {
    final HC<int[], int[]> hc   = new HC<>();
    final BinPackingObjective f = BinPackingObjective.EXAMPLE_PROBLEM;

    hc.nullary = new PermutationNullaryUniform(f.objects.length);
    hc.unary   = new PermutationUnarySwap();

    System.out.println("Hill Climbing");
    for (int i = 1; i < 100; i++) {
      hc.termination = new MaxSteps(100000);
      final Individual<int[], int[]> res = hc.solve(f);
      System.out.println(res.v);
    }
  }
}