// start
package poad;

import java.util.Random;

/**
 * A search operation which receives two points in the search space and creates a new genotype
 * closely related to these parents. It will never modify the inputs/parents.
 * 
 * @param <G>
 *          the search space (genome) containing the genotypes
 */
// end
public interface IBinarySearchOperation<G> {// start

  /**
   * Create a genotype which combines features from parent1 and parent2
   * 
   * @param parent1
   *          the first parent genotype
   * @param parent2
   *          the second parent genotype
   * @param r
   *          the Random number generator which will help us to make random decisions or to generate
   *          random numbers
   * @return a new instance of G which is a combination of parent1 and parent2
   */
  // end
  public abstract G recombine(final G parent1,//
      final G parent2,//
      final Random r);
}