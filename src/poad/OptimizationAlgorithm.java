// start
package poad;

import java.util.Random;

import poad.gpm.IdentityMapping;

/**
 * A base class for optimization algorithms. This class has member variables for different modules
 * like search operations and the method {@link #solve(IObjectiveFunction)} that you must override
 * to implement the algorithm's functionality.
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the problem/solution space
 */
// end
public abstract class OptimizationAlgorithm<G, X> {
  // start
  /** the nullary search operation */
  // end
  public INullarySearchOperation<G> nullary;// start

  /** the unary search operation */
  // end
  public IUnarySearchOperation<G> unary;// start

  /** the termination criterion */
  // end
  public ITerminationCriterion termination;// start

  /** the gpm */
  // end
  public IGPM<G, X> gpm;// start

  /** the random number generator */
  // end
  public final Random random;

  // start

  /** create a set of algorithm parameters */
  @SuppressWarnings("unchecked")
  protected OptimizationAlgorithm() {
    super();
    this.random = new Random();
    this.gpm = ((IGPM<G, X>) (IdentityMapping.IDENTITY_MAPPING));
  }

  /**
   * solve the objective function f and return the best solution discovered
   * 
   * @param f
   *          the objective function
   * @return the best solution discovered
   */
  // end
  public abstract Individual<G, X> solve(final IObjectiveFunction<X> f);
}
