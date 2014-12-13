// start
package poad.examples.robocode;

import poad.IGPM;

/** a GPM for robocode! */
// end
public class RobocodeGPM implements IGPM<double[], String> {
  /** the internal string builder */
  private final StringBuilder m_sb;

  /** the robocode gpm */
  public RobocodeGPM() {
    super();
    this.m_sb = new StringBuilder();
  }

  // start
  /**
   * map a real vector to a string
   * 
   * @param genotype
   *          the genotype
   * @return the string
   */
  @Override
  // end
  public String gpm(final double[] genotype) {
    boolean notfirst = false;
    this.m_sb.setLength(0);
    for (final double d : genotype) {
      if (notfirst) {
        this.m_sb.append(' ');
      } else {
        notfirst = true;
      }
      this.m_sb.append(d);
    }
    return this.m_sb.toString();
  }
}
