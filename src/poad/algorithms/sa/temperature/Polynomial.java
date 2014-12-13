// start
package poad.algorithms.sa.temperature;

import poad.algorithms.sa.ITemperatureSchedule;

/** the polynomial temperature schedule */
// end
public class Polynomial implements ITemperatureSchedule { // start
  /** the start temperature */
  private final double Ts;

  /** the alpha */
  private final double alpha;

  /** the tmax */
  private final double tmax;

  /**
   * the exponential temperature schedule
   * 
   * @param T
   *          the start temperature
   * @param a
   *          the alpha
   * @param tm
   *          the t-max
   */
  public Polynomial(final double T, final double a, final int tm) {
    super();
    this.Ts = T;
    this.tmax = tm;
    this.alpha = a;
  }

  /** {@inheritDoc} */
  @Override
  // end
  public double getTemperature(final int t) {
    return (this.Ts * Math.pow(Math.max(0d, (1d - (t / this.tmax))),//
        this.alpha));
  }
}
