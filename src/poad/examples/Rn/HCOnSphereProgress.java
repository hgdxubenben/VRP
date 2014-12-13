// start
package poad.examples.Rn;

import java.io.File;

import poad.Individual;
import poad.LoggingObjectiveFunction;
import poad.G.Rn.Rn;
import poad.G.Rn.RnNullaryUniform;
import poad.G.Rn.RnUnaryNormal;
import poad.algorithms.HC;
import poad.termination.MaxSteps;

// end

/** a simple test class applying the hill climber to a function */
public class HCOnSphereProgress {// start
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
    final LoggingObjectiveFunction<double[]> f;
    Individual<double[], double[]> result;

    algorithm   = new HC<>();
    searchSpace = new Rn(-10, 10, 5);

    algorithm.nullary = new RnNullaryUniform(searchSpace);
    algorithm.unary   = new RnUnaryNormal(searchSpace);

    f = new LoggingObjectiveFunction<>(new Sphere(searchSpace), new File("./logs/sphereRes"));

    for (int i = 1; i <= 10; i++) {
      algorithm.termination = new MaxSteps(1000000);

      f.beginRun();
      result = algorithm.solve(f);
      f.endRun();

      System.out.println("run " + i + " has result quality " + result.v);
    }
  }
}
