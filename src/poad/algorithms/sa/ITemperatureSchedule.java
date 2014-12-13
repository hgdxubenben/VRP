// start
package poad.algorithms.sa;

/**
 * an interface for the temperature schedule as needed by Simulated Annealing
 */
// end
public interface ITemperatureSchedule {
  // start
  /**
   * compute the temperature at time step t
   * 
   * @param t
   *          the iteration of the algorithm
   * @return the temperature value at that iteration
   */
  // end
  public abstract double getTemperature(final int t);
}