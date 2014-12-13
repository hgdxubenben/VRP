// start
package poad.examples.lesson_02;

import poad.IObjectiveFunction;

/** an objective function for the stone throw problem */
// end
public final class StoneThrowObjective implements IObjectiveFunction<Number> {
  // start
  /**
   * @param x
   *          the starting velocity of the stone throw
   * @return the difference of the throw distance and 100m
   */
  @Override
  // end
  public final double compute(final Number x) {
    final double v = x.doubleValue();
    final double d = (((v * v) / 9.80665d) * Math.sin(((2.0d * 15.0d) / 180.0d) * Math.PI));
    return Math.abs(100d - d);
  }
}
