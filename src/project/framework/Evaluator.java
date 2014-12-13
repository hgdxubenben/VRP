package project.framework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

/**
 * The evaluator class for solution programs. This program takes as input the directory with
 * benchmark/problem instances, the path to your program, and the path where to put the results. It
 * will execute your program on each benchmark instance {@link #RUN_COUNT} times. In total, it
 * grants {@link #RUNTIME} runtime (divided amongst all the runs). The results that we obtain this
 * way are the basis for computing the score of your program with {@link project.framework.Scorer}.
 * 
 * @author Thomas Weise
 */
public class Evaluator {

  /** the total runtime allocated for all scenarios, in milliseconds */
  public static final long RUNTIME = (60 * 60 * 1000);

  /** the number of runs per configuration */
  public static final int RUN_COUNT = Scorer.SAMPLE_COUNT;

  /** the file name of the summary file */
  public static final String SUMMARY_FILE = "summary.txt"; //$NON-NLS-1$

  /**
   * The main method
   * 
   * @param args
   *          the argument list
   * @throws Throwable
   *           if something goes wrong
   */
  public static final void main(final String[] args) throws Throwable {
    File input, run, output, root;
    File[] benchmarks;
    final long timePerRun;
    final ProcessBuilder pb;
    final List<String> cmd;
    String s, lcs, txt;
    int i;

    System.out.println("======= Evaluation Utility ======="); //$NON-NLS-1$
    System.out.println(//
        "This program applies a program to all benchmarks in a directory and collects the results."); //$NON-NLS-1$

    System.out.print("Usage: java "); //$NON-NLS-1$
    System.out.print(Evaluator.class.getCanonicalName());
    System.out.println(//
        " {benchmark dir} {output dir} {class file/binary to execute} {root folder}"); //$NON-NLS-1$

    System.out.println(//
        " benchmark dir: directory holding the scenario files.");//$NON-NLS-1$
    System.out.println(//
        "    output dir: directory where the algorithm output should be placed.");//$NON-NLS-1$
    System.out.println(//
        "  binary/class: path to binary file or Java class to be executed.");//$NON-NLS-1$
    System.out.println(//
        "                e.g.: c:/somedir/src/project/yourwork/SolverTemplate.class");//$NON-NLS-1$
    System.out.println(//
        "   root folder: only for Java classes necessary, path to root package.");//$NON-NLS-1$
    System.out.println(//
        "                e.g.: c:/somedir/src/");//$NON-NLS-1$

    System.out.println();
    // SETUP: read command line parameters

    root = new File(".").getCanonicalFile(); //$NON-NLS-1$
    input = new File(root, "benchmarks"); //$NON-NLS-1$
    output = new File(root, "output"); //$NON-NLS-1$
    run = new File(root, "solver.jar"); //$NON-NLS-1$

    if (args != null) {
      if (args.length > 0) {
        input = new File(args[0]).getCanonicalFile();
        if (args.length > 1) {
          output = new File(args[1]).getCanonicalFile();
          if (args.length > 2) {
            run = new File(args[2]).getCanonicalFile();
            if (args.length > 3) {
              root = new File(args[3]).getCanonicalFile();
            } else {
              root = run.getParentFile().getCanonicalFile();
            }
          }
        }
      }
    }

    // PRINT SETUP

    System.out.println();
    System.out.println("Benchmark Dir: " + input); //$NON-NLS-1$
    System.out.println("Output Dir   : " + output); //$NON-NLS-1$
    System.out.println("Executable   : " + run); //$NON-NLS-1$
    System.out.println("Execution Dir: " + root); //$NON-NLS-1$

    benchmarks = input.listFiles();
    System.out.println();
    System.out.print("There are ");//$NON-NLS-1$
    System.out.print(benchmarks.length);
    System.out.print(" benchmark instances in the benchmark directory ");//$NON-NLS-1$
    System.out.print(input);
    System.out.println('.');

    System.out.print("As we need ");//$NON-NLS-1$
    System.out.print(Evaluator.RUN_COUNT);
    System.out.print(//
        " runs per benchmark instance and have a total runtime of ");//$NON-NLS-1$
    System.out.print(Evaluator.RUNTIME / 1000l);
    System.out.print("s, this means that there are ");//$NON-NLS-1$
    timePerRun = ((Evaluator.RUNTIME / (benchmarks.length * Evaluator.RUN_COUNT)) + 1l);
    System.out.print(timePerRun / 1000);
    System.out.println("s per run.");//$NON-NLS-1$

    System.out.println();

    // SETUP: Creating process builder
    cmd = new ArrayList<>(6);
    pb = new ProcessBuilder();
    pb.directory(root);
    pb.inheritIO();
    pb.command(cmd);
    
    s = run.toString();
    lcs = s.toLowerCase();
    if (lcs.endsWith(".class")) { //$NON-NLS-1$
      cmd.add("java");//$NON-NLS-1$
      txt = root.toString().toLowerCase();
      if (lcs.startsWith(txt)) {
        for (i = txt.length(); i < s.length(); i++) {
          if (s.charAt(i) != File.separatorChar) {
            break;
          }
        }
        s = s.substring(i).replace(File.separatorChar, '.');
        s = s.substring(0, s.length() - 6);
      }
      cmd.add(s);
    } else {
      if (lcs.endsWith(".jar")) {//$NON-NLS-1$
        cmd.add("java");//$NON-NLS-1$
        cmd.add("-jar");//$NON-NLS-1$
        cmd.add(s);
      } else {
        cmd.add(s);
      }
    }

    cmd.add("<inputFile>");//$NON-NLS-1$
    cmd.add("<outputFile>");//$NON-NLS-1$
    cmd.add("<runtime>");//$NON-NLS-1$

    for (final File f : benchmarks) {
      try {
        Evaluator.runBenchmark(f, output, pb, cmd, timePerRun);
      } catch (final Throwable t) {
        //
      }
    }
  }

  /**
   * run the evaluation for one benchmark
   * 
   * @param benchmark
   *          the benchmark
   * @param outputDir
   *          the output directory
   * @param psb
   *          the process builder
   * @param cmd
   *          the command
   * @param runtime
   *          the runtime
   */
  private static final void runBenchmark(final File benchmark, final File outputDir,
      final ProcessBuilder psb, final List<String> cmd, final long runtime) {
    final int cmdSize;
    final File out;
    final Scenario scen;
    final double[] results;
    final Timer timer;
    ProcessKiller pk;
    String s;
    int i;
    File f;
    long time;

    Solution solution;

    System.out.print("Now doing benchmark "); //$NON-NLS-1$
    System.out.println(benchmark);

    results = new double[Evaluator.RUN_COUNT];

    try {
      s = benchmark.getName();
      i = s.indexOf('.');
      if (i > 0) {
        s = s.substring(0, i).trim();
      }
      out = new File(outputDir, s);
      out.mkdirs();

      scen = new Scenario();
      solution = new Solution();
      scen.readFile(benchmark);

      cmdSize = cmd.size();
      cmd.set(cmdSize - 3, benchmark.getCanonicalPath());
      Arrays.fill(results, Double.POSITIVE_INFINITY);
      timer = new Timer(true);
      try {

        for (i = 0; i < Evaluator.RUN_COUNT; i++) {
          f = new File(out, "run_" + i + //$NON-NLS-1$
              ".txt").getCanonicalFile(); //$NON-NLS-1$
          cmd.set(cmdSize - 2, f.getCanonicalPath());
          time = (System.currentTimeMillis() + runtime);
          cmd.set(cmdSize - 1, Long.toString(time));

          System.out.print("Starting run ");//$NON-NLS-1$
          System.out.print(i);
          System.out.print(" which is supposed to terminate on ");//$NON-NLS-1$
          System.out.println(time);

          try {
            pk = new ProcessKiller(psb.start());
            timer.schedule(pk, (runtime + 5000l));
            try {
              try {
                pk.proc.waitFor();
              } finally {
                pk.proc.destroy();
              }
            } catch (final Throwable tt) {
              tt.printStackTrace();
            }
            pk.cancel();
            timer.purge();
            pk = null;

            System.out.println("Run finished, now evaluating."); //$NON-NLS-1$

            try {
              solution.readFile(f);
              results[i] = scen.compute(solution);
            } catch (final Throwable z) {
              z.printStackTrace();
            }

            System.out.print("Evaluated: "); //$NON-NLS-1$" +
            System.out.println(results[i]);
          } catch (final Throwable t) {
            t.printStackTrace();
          }
        }
      } finally {
        timer.cancel();
      }

      f = new File(out, Evaluator.SUMMARY_FILE).getCanonicalFile();

      System.out.print("Writing summary file ");//$NON-NLS-1$"
      System.out.println(f);

      try (final FileWriter fow = new FileWriter(f)) {
        try (final BufferedWriter bw = new BufferedWriter(fow)) {
          Arrays.sort(results);
          for (final double l : results) {
            bw.write(String.valueOf(l));
            bw.newLine();
          }
        }
      }
    } catch (final Throwable t) {
      t.printStackTrace();
    }

    System.out.print("Finished doing benchmark "); //$NON-NLS-1$
    System.out.println(benchmark);
  }
}
