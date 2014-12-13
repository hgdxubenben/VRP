// start
package poad;

/**
 * This interface allows us to provide code that tells the optimization process when to stop.
 */
// end
public interface ITerminationCriterion {// start

  /**
   * This function should be called every single time after IObjectiveFunction.compute(..) was
   * invoked
   * 
   * @return true if we should stop the optimization process, false if we can continue
   */
  // end
  public abstract boolean shouldTerminate();
}