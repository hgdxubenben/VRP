package poad.gpm;

import poad.IGPM;
import poad.G.Rn.Rn;

/** a genotype-phenotype mapping that converts a bit string to a vector of doubles */
public class BitsToDoublesGPM extends Rn implements IGPM<boolean[], double[]> {
  /** instantiate the gpm: accept a record describing the dimension and borders of the
   * sub-space of R^n in which the phenotypes will be located
   * @param rn
   *          the other Rn record */
  public BitsToDoublesGPM(final Rn rn) {
    super(rn);
  }

  /** {@inheritDoc} */
  @Override public double[] gpm(final boolean[] genotype) {
    final int bitsPerDouble;
    final double[] phenotype;
    final double mul;
    int bitIdx;
    long val;
    phenotype = new double[this.dim];
    bitsPerDouble = (genotype.length / this.dim);
    mul = ((this.max - this.min) / ((1l << bitsPerDouble) - 1l));
    bitIdx = 0;
    for (int i = phenotype.length; (--i) >= 0;) {
      val = 0l;
      for (int j = bitsPerDouble; (--j) >= 0;) {
        val <<= 1l;
        if (genotype[bitIdx++]) {
          val |= 1l;
        }
      }
      phenotype[i] = (this.min + (val * mul));
    }
    return phenotype;
  }
}
