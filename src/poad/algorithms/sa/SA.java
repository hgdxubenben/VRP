// start
package poad.algorithms.sa;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.OptimizationAlgorithm;

/**
 * a hill climbing algorithm, exactly as taught in the class
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the solution space
 */
// end
public class SA<G, X> extends OptimizationAlgorithm<G, X> {// start
  /** the temperature schedule to use */
  public ITemperatureSchedule temperature;

  /** instantiate */
  public SA() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  // end
  public Individual<G, X> solve(final IObjectiveFunction<X> f) {
    Individual<G, X> pcur, pnew, pbest;
    double deltaE, T;
    int t;

    pcur = new Individual<>();
    pnew = new Individual<>();
    pbest = new Individual<>();

    t = 1;
    pcur.g = this.nullary.create(this.random);
    pcur.x = this.gpm.gpm(pcur.g);
    pcur.v = f.compute(pcur.x);
    pbest.assign(pcur);

    while (!(this.termination.shouldTerminate())) {
      pnew.g = this.unary.mutate(pcur.g, this.random);
      pnew.x = this.gpm.gpm(pnew.g);
      pnew.v = f.compute(pnew.x);
      deltaE = (pnew.v - pcur.v);

      if (deltaE <= 0d) {
        pcur.assign(pnew);
        if (pnew.v < pbest.v) {
          pbest.assign(pnew);
        }
      } else {
        T = this.temperature.getTemperature(t);
        if (this.random.nextDouble() < Math.exp(-deltaE / T)) {
          pcur.assign(pnew);
        }
      }
      t++;
    }
    return pbest;
  }
}
