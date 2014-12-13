// start
package poad.algorithms.ea;

import poad.IBinarySearchOperation;
import poad.IObjectiveFunction;
import poad.Individual;
import poad.OptimizationAlgorithm;

/**
 * the evolutionary algorithm
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the problem/solution space
 */// end
public class EA<G, X> extends OptimizationAlgorithm<G, X> {// start
  /** the temperature */// end
  public ISelectionAlgorithm selection; // start

  /** the population size */// end
  public int ps;// start

  /** the mating pool */// end
  public int mps;// start

  /** the crossover rate */// end
  public double cr;// start

  /** the binary search operation */// end
  public IBinarySearchOperation<G> binary;// start

  /** create */// end
  public EA() {
    super();
//    this.cr        = /** TODO: Set default */;
//    this.ps        = /** TODO: Set default */;
//    this.mps       = /** TODO: Set default */;
//    this.selection = /** TODO: Set default */;
  }// start

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  // end
  public Individual<G, X> solve(final IObjectiveFunction<X> f) {
    return null;
    /** TODO */
  }
}
