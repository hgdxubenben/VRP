package project.examples;

import poad.IObjectiveFunction;
import poad.Individual;
import poad.algorithms.RandomSampling;
import project.framework.ExperimentRunner;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * A slightly less primitive way to solve the task: random sampling, from the lecture.
 * 
 * @author Thomas Weise
 */
public class RandomSamplingFromLecture extends RandomSampling<Solution, Solution> {

  /** create */
  public RandomSamplingFromLecture() {
    super();
    this.nullary = new ExampleNullaryOperator();
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
    ExperimentRunner.main(new RandomSamplingFromLecture(), args);
  }

}
