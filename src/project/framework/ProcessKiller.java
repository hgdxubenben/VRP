package project.framework;

import java.util.TimerTask;

/** the process killer task */
final class ProcessKiller extends TimerTask {

  /** the process */
  final Process proc;

  /**
   * create
   * 
   * @param p
   *          the process
   */
  protected ProcessKiller(final Process p) {
    super();
    this.proc = p;
  }

  /** {@inheritDoc} */
  @Override
  public void run() {
    System.out.println("Killing process which takes too long..."); //$NON-NLS-1$
    try {
      this.proc.destroy();
    } catch (final Throwable tt) {
      tt.printStackTrace();
    }
  }
}
