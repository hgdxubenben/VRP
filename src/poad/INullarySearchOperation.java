// start
package poad;

import java.util.Random;

/**
 * A search operation which can create initial points in the search space where we begin our
 * optimization process. These points may be randomly created or the result of some clever
 * initialization.
 * 
 * @param <G>
 *          the search space (genome) containing the genotypes
 */
// end
public interface INullarySearchOperation<G> {// start
  /**
   * Create a new point in the search space.
   * 
   * @param r
   *          the Random number generator which will help us to make random decisions or to generate
   *          random numbers
   * @return a new, possibly randomized instance of G
   */
  // end
  public abstract G create(final Random r);
}