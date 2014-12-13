// start
package poad;

/**
 * A record which can store a genotype (the internal data structure that is processed and modified
 * by our algorithm), a phenotype ( the solution data structure we can give to a user and
 * {@link poad.IObjectiveFunction objective function}), and some related information such as its
 * objective value.
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the problem space
 */
// end
public class Individual<G, X> implements Comparable<Individual<G, X>> {
  // start
  /** the "genotype": this data structure is processed by the search operations */
  // end
  public G g;
  // start
  /**
   * the "phenotype": this data structure can be evaluated by the objective function and will be
   * handed to the user as result of the optimization process
   */
  // end
  public X x;
  // start
  /**
   * the objective value or fitness: How good is the solution? Small values are better.
   */
  // end
  public double v;// start

  /** create */
  public Individual() {
    super();
    this.clear();
  }

  /**
   * create
   * 
   * @param genotype
   *          the genotype
   * @param phenotype
   *          the phenotype
   * @param value
   *          the value
   */
  public Individual(final G genotype, final X phenotype, final double value) {
    this();
    this.g = genotype;
    this.x = phenotype;
    this.v = value;
  }

  /** clear this individual record */
  public void clear() {
    this.g = null;
    this.x = null;
    this.v = Double.POSITIVE_INFINITY;
  }

  /**
   * copy another individual record and store all its values into this record
   * 
   * @param copyThis
   *          the individual record to be copied into this one
   */
  public void assign(final Individual<? extends G, ? extends X> copyThis) {
    this.g = copyThis.g;
    this.x = copyThis.x;
    this.v = copyThis.v;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(512);
    sb.append("Quality: ");
    sb.append(this.v);
    if((this.g!=this.x)&&(this.g!=null)){
      sb.append(", Genotype: ");
      sb.append(Utils.toString(this.g));
      sb.append(", Phenotype: ");
    } else {
      sb.append(", Solution: ");
    }
    sb.append(Utils.toString(this.x));
    return sb.toString();
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(final Individual<G, X> o) {
    if (o == null) {
      return (-1);
    }
    if (o == this) {
      return 0;
    }
    return Double.compare(this.v, o.v);
  }
  // end
}