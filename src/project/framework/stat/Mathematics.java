package project.framework.stat;

/**
 * A mathematical helper class
 *
 * @author Thomas Weise
 */
public final class Mathematics {
  /** The natural logarithm of 10 */
  public static final double LN_10 = Math.log(10.0d);

  /** The natural logarithm of 2 */
  public static final double LN_2 = Math.log(2.0d);

  /**
   * The smallest difference of two numbers, if they differ smaller, they are
   * considered as equal
   */
  public static final double EPS = Math.pow(2.0d, -52.0d);

  /** The natural logarithm of EPS */
  public static final double LN_EPS = Math.log(Mathematics.EPS);

  /** The square root of pi */
  public static final double SQRT_PI = Math.sqrt(Math.PI);

  /** The natural logarithm of the square root of pi */
  public static final double LOG_SQRT_PI = Math.log(Mathematics.SQRT_PI);

  /** 1.0 / square root of pi */
  public static final double INV_SQRT_PI = 1.0d / Mathematics.SQRT_PI;

  /** The square root of 2.0 */
  public static final double SQRT_2 = Math.sqrt(2.0d);

  /** The square root of 2*pi. */
  public static final double SQRT_2_PI = Mathematics.SQRT_2
      * Mathematics.SQRT_PI;

  /** The natural logarithm of the square root of pi. */
  public static final double LN_SQRT_PI = Math.log(Mathematics.SQRT_PI);

  /**
   * Euler's (Mascheroni's) constant.
   */
  public static final double EULER_CONSTANT = 0.57721566490153286060651209008240243104215933593992359880576723488486772677766467093694706329174674951463144724980708248096050401448654283622417399764492353625350033374293733773767394279259525824709491600873520394816567d;

  /**
   * Wrap a value so it fits into 0...mod-1
   *
   * @param value
   *          the value to be wrapped
   * @param mod
   *          the modulo operator
   * @return the wrapped value
   */
  public static final int modulo(final int value, final int mod) {
    if (mod == 0) {
      if (value < 0) {
        return Integer.MIN_VALUE;
      }
      return Integer.MAX_VALUE;
    }
    if (value < 0) {
      return (((value % mod) + mod) % mod);
    }
    return (value % mod);
  }

  /**
   * Wrap a value so it fits into 0...mod-1
   *
   * @param value
   *          the value to be wrapped
   * @param mod
   *          the modulo operator
   * @return the wrapped value
   */
  public static final long modulo(final long value, final long mod) {
    if (mod == 0l) {
      if (value < 0l) {
        return Long.MIN_VALUE;
      }
      return Long.MAX_VALUE;
    }
    if (value < 0) {
      return (((value % mod) + mod) % mod);
    }
    return (value % mod);
  }

  /**
   * Perform a division which returns a result rounded to the next higher
   * integer.
   *
   * @param dividend
   *          the divident
   * @param divisor
   *          the divisor
   * @return the result
   */
  public static final int ceilDiv(final int dividend, final int divisor) {

    int r;

    if (divisor == 0) {
      return ((dividend > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
    }
    r = (dividend / divisor);

    if ((r * divisor) == dividend) {
      return r;
    }

    if (dividend < 0) {
      if (divisor < 0) {
        return (r + 1);
      }
      return (r - 1);
    }
    if (divisor < 0) {
      return (r - 1);
    }
    return (r + 1);
  }

  /**
   * Perform a division which returns a result rounded to the next higher
   * integer.
   *
   * @param dividend
   *          the divident
   * @param divisor
   *          the divisor
   * @return the result
   */
  public static final long ceilDiv(final long dividend, final long divisor) {
    long r;

    if (divisor == 0) {
      return ((dividend > 0) ? Long.MAX_VALUE : Long.MIN_VALUE);
    }
    r = (dividend / divisor);

    if ((r * divisor) == dividend) {
      return r;
    }

    if (dividend < 0) {
      if (divisor < 0) {
        return (r + 1);
      }
      return (r - 1);
    }
    if (divisor < 0) {
      return (r - 1);
    }
    return (r + 1);
  }

  /**
   * rounds r to decimals decimals after the dot
   *
   * @param r
   *          The number to round.
   * @param decimals
   *          The count of decimals to round r to. If negative, maybe -1, r
   *          would be rounded to full decades.
   * @return r rounded to decimals decimals.
   */
  public static final double round(final double r, final int decimals) {
    double z;

    if (r == 0) {
      return r;
    }

    z = Math.rint(Math.log10(r));

    if (z < 0) {
      z = decimals - z - 1;
    } else {
      z = decimals;
    }

    if (z < 0.0) {
      z = 1.0 / Math.rint(Math.pow(Math.rint(-z), 10d));
    } else {
      z = Math.rint(Math.pow(Math.rint(z), 10d));
    }

    return Math.rint(r * z) / z;
  }

  /**
   * rounds r to decimals decimals
   *
   * @param r
   *          The number to round.
   * @param decimals
   *          The count of decimals to round r to.
   * @return r rounded to decimals decimals.
   */
  public static final double round2(final double r, final int decimals) {
    double z;

    if (r == 0d) {
      return r;
    }

    z = Math.floor((Math.log10(r) - decimals) + 1);

    if (z >= 0d) {
      z = 1d / Math.rint(Math.pow(10d, z));
    } else {
      z = Math.rint(Math.pow(10d, -z));
    }

    return (Math.rint(r * z) / z);
  }

  /**
   * Check whether something is indeed a number or not
   *
   * @param d
   *          the double variable
   * @return <code>true</code> if <code>d</code> is a number,
   *         <code>false</code> if it is infinit or nan
   */
  public static final boolean isNumber(final double d) {
    return (!((Double.isNaN(d) || Double.isInfinite(d))));
  }
}
