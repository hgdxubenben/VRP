// start
package poad.algorithms.sa.temperature;

import poad.algorithms.sa.ITemperatureSchedule;

/** the exponential temperature schedule */
// end
public class Exponential implements ITemperatureSchedule { // start
  /** the start temperature */
  private final double Ts;

  /** the epsilon */
  private final double epsilon;

  /**
   * the exponential temperature schedule
   * 
   * @param T
   *          the start temperature
   * @param e
   *          the epsilon
   */
  public Exponential(final double T, final double e) {
    super();
    this.Ts = T;
    this.epsilon = e;
  }

  /** {@inheritDoc} */
  @Override
  // end
  public double getTemperature(final int t) {
    return (this.Ts * Math.pow((1d - this.epsilon), t));
  }
}
