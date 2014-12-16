// start
package poad.algorithms;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.OptimizationAlgorithm;
import project.examples.TabuList;

/**
 * a hill climbing algorithm, exactly as taught in the class
 * 
 * @param <G>
 *          the search space
 * @param <X>
 *          the solution space
 */
// end
public class TS<G, X> extends OptimizationAlgorithm<G, X> {
  private IObjectiveFunction<X> f_Scenario;

  // start
  /** instantiate */
  public TS() {
    super();
  }

  // find the best move for the current solution
  @SuppressWarnings("unused")
  private Individual<G, X> getBestNeighbour(Individual<G, X> initSolution, TabuList tabuList) {
    Individual<G, X> bestNeighbour = new Individual<>(), pnew = new Individual<>();
    // initial the best neighbour to the first neighbour
    pnew.g = this.unary.mutate(initSolution.g, tabuList);
    pnew.x = this.gpm.gpm(pnew.g);
    pnew.v = this.f_Scenario.compute(pnew.x);
    bestNeighbour = pnew;

    while (true) {
      pnew.g = this.unary.mutate(initSolution.g, tabuList);
      //we have found all the neighbours
      if(pnew.g == null){
        break;
      }
      pnew.x = this.gpm.gpm(pnew.g);
      pnew.v = this.f_Scenario.compute(pnew.x);
      if (pnew.v > bestNeighbour.v) {
        bestNeighbour = pnew;
      }
    }
    return bestNeighbour;
  }

  /** {@inheritDoc} */
  @Override
  // end
  public Individual<G, X> solve(final IObjectiveFunction<X> f) {
    this.f_Scenario = f;
    Individual<G, X> pstar, pnew, pbest;
    double curProfit = 0, bestProfit = 0;
    TabuList tabuList = new TabuList(2);
    // Solution curSolution , bestSolution;

    pstar = new Individual<>();
    pnew = new Individual<>();
    pbest = new Individual<>();
    pstar.g = this.nullary.create(this.random);
    pstar.x = this.gpm.gpm(pstar.g);
    pstar.v = f.compute(pstar.x);

    while (!(this.termination.shouldTerminate())) {

      pnew = getBestNeighbour(pnew , tabuList);
      curProfit = f.compute(pnew.x);
      if (curProfit > bestProfit) {
        bestProfit = curProfit;
        pbest = pnew;
      }

    }
    return pbest;
  }
}
