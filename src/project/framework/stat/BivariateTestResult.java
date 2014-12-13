package project.framework.stat;

/**
 * The bivariate test result implementation
 *
 * @author Thomas Weise
 */
public class BivariateTestResult {

  /** The statistic a */
  private final double m_statA;

  /** The statistic a */
  private final double m_statB;

  /** the error probability */
  private final double m_errorProb;

  /** the comparison result */
  private final int m_c;

  /**
   * Create a new bivariate test result
   *
   * @param a
   *          the statistic a
   * @param b
   *          the statistic b
   * @param errorProb
   *          the error probability
   */
  public BivariateTestResult(final double a, final double b,
      final double errorProb) {
    super();

    this.m_statA = a;
    this.m_statB = b;
    this.m_errorProb = (((this.m_c = Double.compare(a, b)) != 0) ? errorProb
        : 1d);
  }

  /**
   * Obtain the test result
   *
   * @param alpha
   *          the significance level, i.e., the threshold probability that the
   *          the observed difference (if any) was insignificant
   * @return <0 if the first data sample used for testing was smaller than the
   *         second one, 0 if no significant difference was detected, and >0 if
   *         the second sample was significantly larger than the first one
   */
  public final int result(final double alpha) {
    if (this.m_errorProb > alpha) {
      return 0;
    }

    return this.m_c;
  }

  /**
   * Obtain the probability of a type-one error, i.e., the probability that the
   * the observed difference (if any) was insignificant if assumed to be
   * significant
   *
   * @return p
   */
  public final double errorProb() {
    return this.m_errorProb;
  }

  /**
   * The sample statistic value for the first sample.
   *
   * @return sample statistic value for the first sample
   */
  public final double statisticA() {
    return this.m_statA;
  }

  /**
   * The sample statistic value for the second sample.
   *
   * @return sample statistic value for the second sample
   */
  public final double statisticB() {
    return this.m_statB;
  }

  /**
   * The values to be checked
   */
  private static final double[] CHECK = new double[] {
      0.1d, 0.05d, 0.025d, 0.02d, 0.01d, };

  /**
   * the check chars
   */
  private static final char[][] CHECK_CHRS;

  static {
    int i, l;
    StringBuilder sb;

    i = BivariateTestResult.CHECK.length;
    CHECK_CHRS = new char[i][];
    sb = new StringBuilder(20);

    for (--i; i >= 0; i--) {
      sb.setLength(0);
      sb.append(BivariateTestResult.CHECK[i]);
      while (sb.length() < 5) {
        sb.append('0');
      }
      sb.insert(0, "alpha="); //$NON-NLS-1$
      sb.append(':');
      sb.append(' ');
      BivariateTestResult.CHECK_CHRS[i] = new char[l = sb.length()];
      sb.getChars(0, l, BivariateTestResult.CHECK_CHRS[i], 0);
    }
  }

  /**
   * Switch data samples a and b
   *
   * @return the inverse test result
   */
  public BivariateTestResult invert() {
    return new BivariateTestResult(this.m_statB, this.m_statA,
        this.m_errorProb);
  }

}