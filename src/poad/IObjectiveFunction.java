// start
package poad;

/**
 * An objective function receives a candidate solution (phenotype) as parameter and returns a real
 * value which rates that candidate solution.
 * 
 * @param <X>
 *          the problem space (solution space, phenome) containing the solutions
 */
// end
public interface IObjectiveFunction<X> {
  // start

  /**
   * Compute the objective value for the candidate solution x
   * 
   * @param x
   *          the candidate solution
   * @return the objective value
   */
  // end
  public abstract double compute(final X x);
}
