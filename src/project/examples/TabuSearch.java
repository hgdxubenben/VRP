package project.examples;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.algorithms.TS;
import project.framework.ExperimentRunner;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * The hill climber from the lectures, using a very primitive unary operator...
 * 
 * @author Thomas Weise
 */
public class TabuSearch extends TS<Solution, Solution> {

  /** create */
  public TabuSearch() {
    super();
    //TODO we need to define our own nullary and unary, let's begin this afternoon
    this.nullary = new ExampleNullaryOperator();
    this.unary = new TSUnaryOperator();
  }

  /** {@inheritDoc} */
  @Override
  public final Individual<Solution, Solution> solve(final IObjectiveFunction<Solution> f) {
    ExampleNullaryOperator.scenario = ((Scenario) f);
    return super.solve(f);
  }

  /**
   * The main method
   * 
   * @param args
   *          the argument list
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    ExperimentRunner.main(new TabuSearch(), args);
  }

}
