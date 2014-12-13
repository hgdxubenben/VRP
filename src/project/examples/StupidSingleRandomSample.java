package project.examples;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.OptimizationAlgorithm;
import project.framework.ExperimentRunner;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * A very primitive way of solving the project task: Create one single solution.
 * 
 * @author Thomas Weise
 */
public class StupidSingleRandomSample extends OptimizationAlgorithm<Solution, Solution> {

  /** create */
  public StupidSingleRandomSample() {
    super();
    this.nullary = new ExampleNullaryOperator();
  }

  /**
   * The main method
   * 
   * @param args
   *          the argument list
   */
  public static final void main(final String[] args){
    ExperimentRunner.main(new StupidSingleRandomSample(), args);
  }

  /** {@inheritDoc} */
  @Override
  public final Individual<Solution, Solution> solve(final IObjectiveFunction<Solution> f) {
    Individual<Solution, Solution> ind;

    ExampleNullaryOperator.scenario = ((Scenario) f);

    ind = new Individual<>();
    ind.g = ind.x = this.nullary.create(this.random);
    ind.v = f.compute(ind.x);
    return ind;
  }

}
