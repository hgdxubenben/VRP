package project.framework;

import java.io.File;
import java.io.IOException;

import poad.Individual;
import poad.OptimizationAlgorithm;
import poad.termination.FinishBefore;

/** This class runs your experiment */
public class ExperimentRunner {

  /**
   * The main method: the program receives three arguments:
   * <ol>
   * <li>the path to the file holding a {@link project.framework.Scenario},</li>
   * <li>the path to the file where the solution ( {@link project.framework.Solution} should be
   * stored to, and</li>
   * <li>the system time (see {@link java.lang.System#currentTimeMillis()}) when this process must
   * have terminated -- otherwise it will be killed and no solution is produced.</li>
   * </ol>
   * 
   * @param solver
   *          the solver to use
   * @param args
   *          the array with the arguments
   */
  public static final void main(final OptimizationAlgorithm<?, Solution> solver, final String[] args) {
    final Individual<?, Solution> indi;

    Scenario scenario;
    Solution solution;

    System.out.println("Experiment Runner"); //$NON-NLS-1$
    System.out
        .print("Call this method from your main method and pass in your algorithm class and the following command line parameters:"); //$NON-NLS-1$
    System.out.println(//
        " {path to scenario/input file} {path to solution/output file} {end time}"); //$NON-NLS-1$

    System.out.println("  input file: path to the file holding a scenario");//$NON-NLS-1$
    System.out.println(" output file: path to file to store the solution in");//$NON-NLS-1$
    System.out.println(//
        "    end time: absolute time stamp when program must have terminated");//$NON-NLS-1$
    System.out.println();

    if ((args == null) || (args.length < 2)) {
      System.out.println("Arguments missing, exiting.");//$NON-NLS-1$
      return;
    }

    // read the scenario
    scenario = new Scenario();
    try {
      scenario.readFile(new File(args[0]));
    } catch (IOException ioe) {
      System.err.println("Error loading scenario.");//$NON-NLS-1$
      ioe.printStackTrace(System.err);
    }

    // get time limit and also allocate some time for file writing!
    solver.termination = new FinishBefore(Long.parseLong(args[2]) - 1000l);

    // call the method that you are supposed to implement
    indi = solver.solve(scenario);
    if (indi != null) {
      solution = indi.x;

      // store the solution
      if (solution != null) {
        try {
          solution.writeFile(new File(args[1]));
        } catch (IOException ioe) {
          System.err.println("Error storing solution.");//$NON-NLS-1$
          ioe.printStackTrace(System.err);
        }

      }
    }

    System.out.println("End.");//$NON-NLS-1$
  }
}
