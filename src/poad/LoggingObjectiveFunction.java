package poad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * An objective function that logs the improvements along the run.
 * 
 * @param <X>
 *          the problem space (solution space) containing the solutions
 */
public class LoggingObjectiveFunction<X> implements IObjectiveFunction<X> {
  /** the objective function */
  private final IObjectiveFunction<X> m_f;

  /** the directory to write log files in */
  private final File m_dir;

  /** the function evaluations */
  private long m_fes;

  /** the m_best objective value */
  private double m_best;

  /** the current buffered writer */
  private BufferedWriter m_bw;

  /** log for the solutions, or {@code null}? */
  private BufferedWriter m_solutions;

  /** should we also log solutions? */
  private final boolean m_logSolutions;

  /**
   * Create logging objective function
   * 
   * @param _f
   *          the function
   * @param d
   *          the directory for the log files
   */
  public LoggingObjectiveFunction(final IObjectiveFunction<X> _f, final File d) {
    this(_f, d, true);
  }

  /**
   * Create logging objective function
   * 
   * @param _f
   *          the function
   * @param d
   *          the directory for the log files
   * @param logSolutions
   *          should we also log the solutions=
   */
  public LoggingObjectiveFunction(final IObjectiveFunction<X> _f, final File d,
      final boolean logSolutions) {
    super();
    File z;
    this.m_f = _f;
    try {
      z = d.getCanonicalFile();
    } catch (final Throwable t) {
      try {
        z = d.getAbsoluteFile();
      } catch (final Throwable t2) {
        z = d;
      }
    }
    this.m_dir = z;
    this.m_logSolutions = logSolutions;
  }

  /** begin a new run */
  public final void beginRun() {
    int i;
    File g, s;

    this.m_bw = null;
    this.m_solutions = null;
    this.m_fes = 0l;
    this.m_best = Double.POSITIVE_INFINITY;

    this.m_dir.mkdirs();

    outer: for (i = 1;; i++) {
      g = new File(this.m_dir, (String.valueOf(i) + ".txt")); //$NON-NLS-1$
      try {
        if (g.createNewFile()) {

          if (this.m_logSolutions) {
            s = new File(this.m_dir, (String.valueOf(i) + "_solutions.txt")); //$NON-NLS-1$
            if (!(s.createNewFile())) {
              continue outer;
            }
            this.m_solutions = new BufferedWriter(new FileWriter(s));
          }

          this.m_bw = new BufferedWriter(new FileWriter(g));
          return;
        }
      } catch (final Throwable t) {
        //
      }
    }
  }

  /** end a run */
  public final void endRun() {
    try {
      try {
        this.m_bw.close();
      } finally {
        try {
          if (this.m_solutions != null) {
            this.m_solutions.close();
          }
        } finally {
          this.m_solutions = null;
        }
      }
    } catch (final Throwable z) {
      //
    } finally {
      this.m_bw = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final X x) {
    final double res = this.m_f.compute(x);

    this.m_fes++;

    if (res < this.m_best) {
      try {
        this.m_best = res;
        this.m_bw.write(String.valueOf(this.m_fes));
        this.m_bw.write('\t');
        this.m_bw.write(String.valueOf(this.m_best));
        this.m_bw.newLine();
        this.m_bw.flush();

        if (this.m_solutions != null) {
          this.m_solutions.write(Utils.toString(x));
          this.m_solutions.newLine();
          this.m_solutions.flush();
        }
      } catch (final Throwable t) {
        //
      }
    }
    return res;
  }
}
