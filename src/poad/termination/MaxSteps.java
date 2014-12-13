// start
package poad.termination;

import poad.ITerminationCriterion;

/** a termination criterion stopping after a given number of steps */
// end
public class MaxSteps implements ITerminationCriterion {
  /** the number of remaining steps */
  private int m_remaining;

  // start
  /**
   * create a new instance
   * 
   * @param steps
   *          the maximum amount of steps
   */
  // end
  public MaxSteps(final int steps) {
    super();
    this.m_remaining = steps;
  }

  // start
  /** {@inheritDoc} */
  @Override
  // end
  public boolean shouldTerminate() {
    return ((--this.m_remaining) < 0);
  }
}
