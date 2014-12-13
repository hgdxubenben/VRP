package poad.termination;

import poad.ITerminationCriterion;

/** a termination criterion stopping at the given tile */
public class FinishBefore implements ITerminationCriterion {
  /** the end runtime */
  final long end;

  /** the end */
  volatile boolean ended;

  /**
   * create a new instance
   * 
   * @param t
   *          the number of milliseconds
   */
  public FinishBefore(final long t) {
    super();
    this.end = (t - 10l);
    new Waiter().start();
  }

  /** {@inheritDoc} */
  @Override
  public boolean shouldTerminate() {
    return this.ended;
  }

  /** the waiter thread */
  private final class Waiter extends Thread {
    /** the thread */
    Waiter() {
      super();
      this.setDaemon(true);
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      long t;
      for (;;) {
        t = (FinishBefore.this.end - System.currentTimeMillis());
        if (t <= 0l) {
          FinishBefore.this.ended = true;
          return;
        }
        try {
          Thread.sleep(t);
        } catch (final Throwable xx) {
          xx.printStackTrace();
        }
      }
    }
  }
}
