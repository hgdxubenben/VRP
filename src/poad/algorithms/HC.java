// start
package poad.algorithms;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.OptimizationAlgorithm;

/**
 * a hill climbing algorithm, exactly as taught in the class
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the solution space
 */
// end
public class HC<G, X> extends OptimizationAlgorithm<G, X> {// start
  /** instantiate */
  public HC() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  // end
  public Individual<G, X> solve(final IObjectiveFunction<X> f) {
    Individual<G, X> pstar, pnew;

    pstar   = new Individual<>();
    pnew    = new Individual<>();
    pstar.g = this.nullary.create(this.random);
    pstar.x = this.gpm.gpm(pstar.g);
    pstar.v = f.compute(pstar.x);

    while (!(this.termination.shouldTerminate())) {
      pnew.g = this.unary.mutate(pstar.g, this.random);
      pnew.x = this.gpm.gpm(pnew.g);
      pnew.v = f.compute(pnew.x);

      if (pnew.v <= pstar.v) {
        pstar.assign(pnew);
      }
    }
    return pstar;
  }
}
