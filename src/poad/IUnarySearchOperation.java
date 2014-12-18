// start
package poad;

import java.util.Random;


/**
 * A search operation which receives one point in the search space and creates a modified copy of
 * that point. It will never change the point parent itself, but make a copy which is somewhat
 * different, maybe randomly mutated.
 * 
 * @param <G>
 *          the search space (genome) containing the genotypes
 */
// end
public interface IUnarySearchOperation<G> {// start

  /**
   * Create a changed (modified, mutated) copy of parent
   * 
   * @param parent
   *          the parent genotype: we want to get a slightly different copy
   * @param r
   *          the Random number generator which will help us to make random decisions or to generate
   *          random numbers
   * @return a new instance of G which is a bit different from parent
   */
  // end
  public abstract G mutate(final G parent, //
      final Random r);

  public abstract G mutate(final G parent);
}