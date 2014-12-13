package project.examples;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.algorithms.HC;
import project.framework.ExperimentRunner;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * The hill climber from the lectures, using a very primitive unary operator...
 * 
 * @author Thomas Weise
 */
public class HillClimbingFromLecture extends HC<Solution, Solution> {

  /** create */
  public HillClimbingFromLecture() {
    super();
    this.nullary = new ExampleNullaryOperator();
    this.unary = new ExampleUnaryOperator();
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
    ExperimentRunner.main(new HillClimbingFromLecture(), args);
  }

}
