// start
package poad.G.Rn;

/**
 * the base class for search operations in Rn: holds the dimension and the maximum and minimum
 */
// end
public class Rn {
  /** the maximum coordinate value */
  public final double max;

  /** the minimum coordinate value */
  public final double min;

  /** the dimension */
  public final int dim;// start

  /**
   * instantiate
   * 
   * @param mi
   *          the minimum
   * @param ma
   *          the maximum
   * @param d
   *          the dimension
   */
  public Rn(final double mi, final double ma, final int d) {
    super();
    this.min = mi;
    this.max = ma;
    this.dim = d;
  }

  /**
   * instantiate by copying another record
   * 
   * @param rn
   *          the other Rn record
   */
  public Rn(final Rn rn) {
    this(rn.min, rn.max, rn.dim);
  }// end
}
