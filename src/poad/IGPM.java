// start
package poad;

/**
 * A genotype-phenotype mapping maps the genotypes from the search space {@code G} (the data
 * structure where the search operations work on) to the phenotypes within the problem space
 * {@code X} (the data structures handed to the objective function and to the users).
 * 
 * @param <G>
 *          the search space (genome) containing the genotypes
 * @param <X>
 *          the problem space (solution space, phenome) containing the phenotypes (candidate
 *          solutions)
 */
// end
public interface IGPM<G, X> {// start

  /**
   * Map a genotype to a phenotype
   * 
   * @param genotype
   *          the genotype
   * @return the phenotype which belongs to the genotype
   */
  // end
  public abstract X gpm(final G genotype);
}
