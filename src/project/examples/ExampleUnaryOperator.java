package project.examples;

import java.util.Random;

import poad.IUnarySearchOperation;
import project.framework.Solution;

/**
 * This operator modifies an existing solution by swapping two orders.
 */
public class ExampleUnaryOperator implements IUnarySearchOperation<Solution> {

  /** Create. */
  public ExampleUnaryOperator() {
    super();
  }

  /**
   * create a new solution by swapping two orders in an existing one
   * 
   * @param parent
   *          the parent solution
   * @param r
   *          than randomizer
   */
  @Override
  public Solution mutate(Solution parent, Random r) {
    int[][] data;
    int i, j, t;

    data = parent.copyData();

    // only the first car is used: data[0]
    i = r.nextInt(data[0].length);
    do {
      j = r.nextInt(data[0].length);
    } while (i == j);

    // swap the location of two orders
    t = data[0][i];
    data[0][i] = data[0][j];
    data[0][j] = t;

    return new Solution(data);
  }

}
