package project.examples;

import java.util.Random;

import poad.INullarySearchOperation;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * This operator creates a single, random candidate solution.
 */
public class ExampleNullaryOperator implements INullarySearchOperation<Solution> {

  /** the scenario */
  static Scenario scenario;

  /** Create */
  public ExampleNullaryOperator() {
    super();
  }

  /**
   * Create a random solution. Basic idea: create a permutation of all the orders (the int array).
   * The array holds each order ID two times, the first time for pick-up, the second time for
   * delivery. Here, we just keep these two occurrences together, i.e., get permutations like
   * "1 1 5 5 3 3 2 2 4 4". This way, we ensure that the vehicle's capacity is never exceeded.
   * 
   * @param r
   *          the randomizer
   * @return the solution
   */
  @Override
  public Solution create(Random r) {
    final int oc;
    final int[] solution;
    int j, i, k, a, b;

    oc = scenario.orderCount();
    j = (oc << 1);
    solution = new int[j];
    for (i = oc; (--i) >= 0;) {
      solution[--j] = i;
      solution[--j] = i;
    }

    for (i = oc; (--i) > 0;) {
      j = r.nextInt(i);

      a = (i << 1);
      b = (j << 1);
      k = solution[a];
      solution[a] = solution[b];
      solution[b] = k;

      a++;
      b++;
      k = solution[a];
      solution[a] = solution[b];
      solution[b] = k;
    }

    return new Solution(solution);
  }
}
