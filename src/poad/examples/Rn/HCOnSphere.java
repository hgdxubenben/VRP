// start
package poad.examples.Rn;

import poad.Individual;
import poad.G.Rn.Rn;
import poad.G.Rn.RnNullaryUniform;
import poad.G.Rn.RnUnaryNormal;
import poad.algorithms.HC;
import poad.termination.MaxSteps;

// end

/** a simple test class applying the hill climber to a function */
public class HCOnSphere {// start
  /**
   * Run the test class
   * 
   * @param args
   *          the command line arguments
   */
  // end
  public static void main(final String[] args) {
    final HC<double[], double[]> algorithm;
    final Rn searchSpace;
    Individual<double[], double[]> result;

    algorithm   = new HC<>();
    searchSpace = new Rn(-10, 10, 5);

    algorithm.nullary = new RnNullaryUniform(searchSpace);
    algorithm.unary   = new RnUnaryNormal(searchSpace);

    for (int i = 1; i < 100; i++) {
      algorithm.termination = new MaxSteps(1000000);
      result = algorithm.solve(new Sphere(searchSpace));

      System.out.println("run " + i + " has result quality " + result.v);
    }
  }
}
