// start
package poad.algorithms.sa.temperature;

import poad.algorithms.sa.ITemperatureSchedule;

/** the logarithmic temperature schedule */
// end
public class Logarithmic implements ITemperatureSchedule { // start
  /** the start temperature */
  private final double Ts;

  /**
   * the exponential temperature schedule
   * 
   * @param T
   *          the start temperature
   */
  public Logarithmic(final double T) {
    super();
    this.Ts = T;
  }

  /** {@inheritDoc} */
  @Override
  // end
  public double getTemperature(final int t) {
    if (t < 3) {
      return this.Ts;
    }
    return (this.Ts / Math.log(t));
  }
}
