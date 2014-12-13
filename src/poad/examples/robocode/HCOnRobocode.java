// start
package poad.examples.robocode;

import poad.G.Rn.Rn;
import poad.G.Rn.RnNullaryUniform;
import poad.G.Rn.RnUnaryNormal2;
import poad.algorithms.HC;
import poad.termination.MaxSteps;

/** apply hill climbing to robocode! */
// end
public class HCOnRobocode {// start
  /**
   * the main routine
   * 
   * @param args
   *          ignored
   */
  // end
  public static void main(final String[] args) {
    final HC<double[], String> hc = new HC<>();
    final Rn rn = new Rn(-1.5d, 1.5d, 20);

    hc.gpm = new RobocodeGPM();
    hc.nullary = new RnNullaryUniform(rn);
    hc.unary = new RnUnaryNormal2(rn);
    hc.termination = new MaxSteps(5000);

    System.out.println(hc.solve(new RobocodeObjective()));
  }
}
