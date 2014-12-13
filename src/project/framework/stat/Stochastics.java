package project.framework.stat;

/**
 * Some stochastic functions
 *
 * @author Thomas Weise
 */
public final class Stochastics {

  /**
   * The natural logarithm of Double.MAX_VALUE, or, in other words, the biggest
   * value where <code>Math.exp(x)</code> produces an exact result.
   *
   * @see Double#MAX_VALUE
   */
  private static final double LN_MAX_DOUBLE = Math.log(Double.MAX_VALUE);

  /**
   * The natural logarithm of Double.MIN_VALUE, or, in other words, the
   * smallest value where <code>Math.exp(x)</code> produces an exact result.
   *
   * @see Double#MIN_VALUE
   */
  private static final double LN_MIN_DOUBLE = Math.log(Double.MIN_VALUE);

  // /
  // / Constants used to calculate the gamma function.
  // /
  /**
   * The calculus constant GAMMA_A.
   */
  private static final double GAMMA_A = (Mathematics.SQRT_2_PI * 1.000000000190015d);

  /**
   * The calculus constant GAMMA_B.
   */
  private static final double GAMMA_B = (Mathematics.SQRT_2_PI * 76.18009172947146d);

  /**
   * The calculus constant GAMMA_C.
   */
  private static final double GAMMA_C = (Mathematics.SQRT_2_PI * -86.50532032941677d);

  /**
   * The calculus constant GAMMA_D.
   */
  private static final double GAMMA_D = (Mathematics.SQRT_2_PI * 24.01409824083091d);

  /**
   * The calculus constant GAMMA_E.
   */
  private static final double GAMMA_E = (Mathematics.SQRT_2_PI * -1.231739572450155d);

  /**
   * The calculus constant GAMMA_F.
   */
  private static final double GAMMA_F = (Mathematics.SQRT_2_PI * 0.1208650973866179E-2d);

  /**
   * The calculus constant GAMMA_G.
   */
  private static final double GAMMA_G = (Mathematics.SQRT_2_PI * -0.5395239384953E-5d);

  /**
   * Another precision factor.
   */
  private static final double GAMMA_MIN_DP = 1e-30d;

  // /
  // / Constants for the computation of the Inverse Normal Cummulative
  // / Distribution.
  // /

  /**
   * A helper value for the computation of the Inverse Normal Cummulative
   * Distribution.
   */
  private static final double ICDF_P_LOW = 0.02425D;

  /**
   * A helper value for the computation of the Inverse Normal Cummulative
   * Distribution.
   */
  private static final double ICDF_P_HIGH = 1.0D - Stochastics.ICDF_P_LOW;

  /**
   * Coefficients A in rational approximations of the Inverse Normal
   * Cummulative Distribution.
   */
  private static final double ICDF_A[] = {
      -3.969683028665376e+01, 2.209460984245205e+02, -2.759285104469687e+02,
      1.383577518672690e+02, -3.066479806614716e+01, 2.506628277459239e+00 };

  /**
   * Coefficients B in rational approximations of the Inverse Normal
   * Cummulative Distribution.
   */
  private static final double ICDF_B[] = {
      -5.447609879822406e+01, 1.615858368580409e+02, -1.556989798598866e+02,
      6.680131188771972e+01, -1.328068155288572e+01 };

  /**
   * Coefficients C in rational approximations of the Inverse Normal
   * Cummulative Distribution.
   */
  private static final double ICDF_C[] = {
      -7.784894002430293e-03, -3.223964580411365e-01, -2.400758277161838e+00,
      -2.549732539343734e+00, 4.374664141464968e+00, 2.938163982698783e+00 };

  /**
   * Coefficients D in rational approximations of the Inverse Normal
   * Cummulative Distribution.
   */
  private static final double ICDF_D[] = {
      7.784695709041462e-03, 3.224671290700398e-01, 2.445134137142996e+00,
      3.754408661907416e+00 };

  // /
  // / Constants used for the erfc-functions
  // /
  /**
   * Coefficients for approximation to erf in first interval
   */
  private static final double ERF_A[] = {
      3.16112374387056560E00, 1.13864154151050156E02, 3.77485237685302021E02,
      3.20937758913846947E03, 1.85777706184603153E-1 };

  /**
   * Coefficients for approximation to erf in first interval
   */
  private static final double ERF_B[] = {
      2.36012909523441209E01, 2.44024637934444173E02, 1.28261652607737228E03,
      2.84423683343917062E03 };

  /**
   * Coefficients for approximation to erfc in second interval
   */
  private static final double ERF_C[] = {
      5.64188496988670089E-1, 8.88314979438837594E0, 6.61191906371416295E01,
      2.98635138197400131E02, 8.81952221241769090E02, 1.71204761263407058E03,
      2.05107837782607147E03, 1.23033935479799725E03, 2.15311535474403846E-8 };

  /**
   * Coefficients for approximation to erfc in second interval
   */
  private static final double ERF_D[] = {
      1.57449261107098347E01, 1.17693950891312499E02, 5.37181101862009858E02,
      1.62138957456669019E03, 3.29079923573345963E03, 4.36261909014324716E03,
      3.43936767414372164E03, 1.23033935480374942E03 };

  /**
   * Coefficients for approximation to erfc in third interval
   */
  private static final double ERF_P[] = {
      3.05326634961232344E-1, 3.60344899949804439E-1, 1.25781726111229246E-1,
      1.60837851487422766E-2, 6.58749161529837803E-4, 1.63153871373020978E-2 };

  /**
   * Coefficients for approximation to erfc in third interval
   */
  private static final double ERF_Q[] = {
      2.56852019228982242E00, 1.87295284992346047E00, 5.27905102951428412E-1,
      6.05183413124413191E-2, 2.33520497626869185E-3 };

  //
  // Constants for the digamma function
  //
  /**
   * The helper constant for the digamme (psi) function.
   */
  private static final double[] DIGAMMA_KNOCE = {
      0.30459198558715155634315638246624251d,
      0.72037977439182833573548891941219706d,
      -0.12454959243861367729528855995001087d,
      0.27769457331927827002810119567456810e-1d,
      -0.67762371439822456447373550186163070e-2d,
      0.17238755142247705209823876688592170e-2d,
      -0.44817699064252933515310345718960928e-3d,
      0.11793660000155572716272710617753373e-3d,
      -0.31253894280980134452125172274246963e-4d,
      0.83173997012173283398932708991137488e-5d,
      -0.22191427643780045431149221890172210e-5d,
      0.59302266729329346291029599913617915e-6d,
      -0.15863051191470655433559920279603632e-6d,
      0.42459203983193603241777510648681429e-7d,
      -0.11369129616951114238848106591780146e-7d,
      0.304502217295931698401459168423403510e-8d,
      -0.81568455080753152802915013641723686e-9d,
      0.21852324749975455125936715817306383e-9d,
      -0.58546491441689515680751900276454407e-10d,
      0.15686348450871204869813586459513648e-10d,
      -0.42029496273143231373796179302482033e-11d,
      0.11261435719264907097227520956710754e-11d,
      -0.30174353636860279765375177200637590e-12d,
      0.80850955256389526647406571868193768e-13d,
      -0.21663779809421233144009565199997351e-13d,
      0.58047634271339391495076374966835526e-14d,
      -0.15553767189204733561108869588173845e-14d,
      0.41676108598040807753707828039353330e-15d,
      -0.11167065064221317094734023242188463e-15d };

  /**
   * The erf threshold
   */
  private static final double ERF_THRESHOLD = 0.46875D;

  /**
   * Hardware dependant constant for erf.
   */
  private static final double ERF_X_NEG = -9.38241396824444;

  /**
   * Hardware dependant constant for erf.
   */
  private static final double ERF_X_BIG = 9.194E0;

  /**
   * Hardware dependant constant for erf.
   */
  private static final double ERF_X_HUGE;

  /**
   * Hardware dependant constant for erf.
   */
  private static final double ERF_X_MAX = Math.min(Double.MAX_VALUE,
      (1.0d / (Math.sqrt(Math.PI) * Double.MIN_VALUE)));

  // /

  // /
  // / Bessel constants.
  // /
  /**
   * The bessel acc value.
   */
  private static final double BESSEL_ACC = 40.0d;

  /**
   * The bessel big value.
   */
  private static final double BESSEL_BIGNO = 1.0e+10d;

  /**
   * The bessek small value.
   */
  private static final double BESSEL_BIGNI = 1.0e-10d;

  // /
  // / Constants for the incomplete complemented gamma function.
  // /
  /**
   * First helper.
   */
  private static final double ICG_BIG;

  /**
   * Second helper.
   */
  private static final double ICG_BIGINV;

  /**
   * The maximum value where gamma(x) returns an exact result.
   */
  private static final double MAX_GAMMA;

  static {
    double d, s, m;

    ICG_BIG = (2.0d * Mathematics.EPS);
    ICG_BIGINV = (1.0d / Stochastics.ICG_BIG);

    ERF_X_HUGE = (1.0d / (2.0d * Math.sqrt(Mathematics.EPS)));

    //
    // Determine the maximum value for the gamma function.
    //
    d = 1.0;
    s = 1E3;

    while (d != s) {
      m = (d + s) * 0.5;
      if (Double.isInfinite(Stochastics.gamma(m))) {
        s = m - (Mathematics.EPS * m);
      } else {
        d = m + (Mathematics.EPS * m);
      }
    }

    MAX_GAMMA = d;
  }

  /**
   * Returns the gamma function at <code>z</code>.
   *
   * @param z
   *          a real number
   * @return <code> &Gamma;(z) = &int;<sub>0</sub><sup>&infin;</sup> t<sup>z-1</sup>e<sup>-t</sup>dt</code>
   */
  public static final double gamma(final double z) {
    double d;
    boolean b;

    b = (z <= 0.5d);
    d = (b ? (1.0d - z) : z);
    d = Math.exp((Math.log(d + 5.5d) * (d + 0.5d)) - (d + 5.5d))
        * ((Stochastics.GAMMA_A + (Stochastics.GAMMA_B / (d + 1.0d))
            + (Stochastics.GAMMA_C / (d + 2.0d))
            + (Stochastics.GAMMA_D / (d + 3.0d))
            + (Stochastics.GAMMA_E / (d + 4.0d))
            + (Stochastics.GAMMA_F / (d + 5.0d)) + (Stochastics.GAMMA_G / (d + 6.0d))) / d);
    if (b) {
      d = ((Math.PI / d) / Math.sin(Math.PI * z));
    }

    if (Math.abs(z - Math.rint(z)) <= Mathematics.EPS) {
      return Math.rint(d);
    }
    return d;
  }

  /**
   * Returns the logarithm of the gamma function at <code>z</code>.
   *
   * @param z
   *          a real number
   * @return <code>log( &Gamma;(z) ) = log ( &int;<sub>0</sub><sup>&infin;</sup> t<sup>z-1</sup>e<sup>-t</sup>dt )</code>
   */
  public static final double gammaLn(final double z) {
    double d;
    boolean b;

    b = (z <= 0.5d);
    d = (b ? (1.0d - z) : z);

    d = (((Math.log(d + 5.5d) * (d + 0.5d)) - (d + 5.5d)) + Math
        .log((Stochastics.GAMMA_A + (Stochastics.GAMMA_B / (d + 1.0d))
            + (Stochastics.GAMMA_C / (d + 2.0d))
            + (Stochastics.GAMMA_D / (d + 3.0d))
            + (Stochastics.GAMMA_E / (d + 4.0d))
            + (Stochastics.GAMMA_F / (d + 5.0d)) + (Stochastics.GAMMA_G / (d + 6.0d)))
            / d));
    if (b) {
      return (Math.log(Math.PI / Math.sin(Math.PI * z)) - d);
    }
    return d;
  }

  /**
   * Returns the lower incomplete gamma function at <code>a, x</code>.
   *
   * @param a
   *          a positive real number
   * @param x
   *          a real number greater than <code>a</code>
   * @return The lower incomplete gamma function at <code>a, x</code>.
   */
  public final static double gammaLowerIncomplete(final double a,
      final double x) {
    return Stochastics.gamma(a) - Stochastics.gammaUpperIncomplete(a, x);
  }

  /**
   * Returns the upper incomplete gamma function at <code>a, x</code>.
   *
   * @param a
   *          a positive real number
   * @param x
   *          a real number greater than <code>a</code>
   * @return <code>&Gamma;( a, x ) = &int;<sub>0</sub><sup>&infin;</sup> t<sup>z-1</sup>e<sup>-t</sup>dt</code>
   */
  public final static double gammaUpperIncomplete(final double a,
      final double x) {
    double b, delta, od, c, d, h, an, i;

    if (x < (a + 1.0d)) {
      d = Stochastics.gamma(a);
      b = a;
      delta = 1.0d / a;
      od = -delta;
      c = delta;

      while (delta != od) // (Math.abs(delta) > Math.abs(sum) *
      // Mathematics.EPS) )
      {
        od = delta;
        b = (1.0d + b);
        delta *= (x / b);
        c += delta;
      }

      return d - (c * Math.exp((a * Math.log(x)) - x));
    }

    b = ((x + 1.0d) - a);
    c = (1.0d / Stochastics.GAMMA_MIN_DP);
    d = (1.0d / b);
    h = d;
    delta = 1.0d;
    i = 1.0d;

    do {
      od = h;
      an = (-i * (i - a));
      i = (i + 1.0d);
      b += 2.0d;
      d = ((an * d) + b);
      c = ((an / c) + b);

      if (Math.abs(c) < Stochastics.GAMMA_MIN_DP) {
        c = Stochastics.GAMMA_MIN_DP;
      }
      if (Math.abs(d) < Stochastics.GAMMA_MIN_DP) {
        d = Stochastics.GAMMA_MIN_DP;
      }

      d = (1.0d / d);
      delta = (c * d);
      h *= delta;

    } while (od != h);

    return h * Math.exp((a * Math.log(x)) - x);
  }

  /**
   * Compute the regularized q gamma function.
   *
   * @param a
   *          double value
   * @param x
   *          double value
   * @return The regularized q gamma function.
   */
  public static final double gammaRegularizedQ(final double a, final double x) {
    double ans, ax, c, yc, r, t, y, z, pk, pkm1, pkm2, qk, qkm1, qkm2, ot;

    if ((x <= 0.0d) || (a <= 0.0d)) {
      return 1.0d;
    }

    if ((x < 1.0d) || (x < a)) {
      return 1.0d - Stochastics.gammaRegularizedP(a, x);
    }

    ax = (a * Math.log(x)) - x - Stochastics.gammaLn(a);
    if (ax < -Stochastics.LN_MAX_DOUBLE) {
      return 0.0d;
    }

    ax = Math.exp(ax);
    y = (1.0d - a);
    z = x + y + 1.0d;
    c = 0.0d;
    pkm2 = 1.0d;
    qkm2 = x;
    pkm1 = x + 1.0d;
    qkm1 = z * x;
    ans = pkm1 / qkm1;
    t = Double.NaN;

    do {
      ot = t;
      c += 1.0;
      y += 1.0;
      z += 2.0;
      yc = y * c;
      pk = (pkm1 * z) - (pkm2 * yc);
      qk = (qkm1 * z) - (qkm2 * yc);

      if (qk != 0.0d) {
        r = pk / qk;
        t = Math.abs((ans - r) / r);
        ans = r;
      } else {
        t = 1.0d;
      }

      pkm2 = pkm1;
      pkm1 = pk;
      qkm2 = qkm1;
      qkm1 = qk;

      if (Math.abs(pk) > Stochastics.ICG_BIG) {
        pkm2 *= Stochastics.ICG_BIGINV;
        pkm1 *= Stochastics.ICG_BIGINV;
        qkm2 *= Stochastics.ICG_BIGINV;
        qkm1 *= Stochastics.ICG_BIGINV;
      }
    } while (ot != t);

    return ans * ax;
  }

  /**
   * Computes the regularized p gamma function.
   *
   * @param a
   *          double value
   * @param x
   *          double value
   * @return The regularized p gamma function.
   */
  public final static double gammaRegularizedP(final double a, final double x) {
    double ans, ax, c, r;

    if ((x <= 0.0d) || (a <= 0.0d)) {
      return 0.0d;
    }

    if ((x > 1.0d) && (x > a)) {
      return 1.0d - Stochastics.gammaRegularizedQ(a, x);
    }

    ax = (a * Math.log(x)) - x - Stochastics.gammaLn(a);
    if (ax < -Stochastics.LN_MAX_DOUBLE) {
      return 0.0d;
    }

    ax = Math.exp(ax);

    r = a;
    c = 1.0d;
    ans = 1.0d;

    do {
      r += 1.0d;
      c *= x / r;
      ans += c;
    } while ((c / ans) > 1E-10);

    return (ans * (ax / a));
  }

  /**
   * Compute the Inverse Normal Cummulative Distribution.
   * <p>
   * Original algorythm and Perl implementation can be found at:
   * http://www.math.uio.no/~jacklam/notes/invnorm/index.html Author: Peter J.
   * Acklam jacklam@math.uio.no
   * </p>
   *
   * @param d
   *          The value to compute the Inverse Normal Cummulative Distribution
   *          for.
   * @return The value of the inverse normal cummulative for the gaussian
   *         distribution.
   */
  public static final double normalInvCDF(final double d) {
    double z, r;

    z = 0;

    if (d == 0.0d) {
      z = Double.NEGATIVE_INFINITY;
    } else if (d == 1.0d) {
      z = Double.POSITIVE_INFINITY;
    } else if ((d < 0) || (d > 1) || Double.isNaN(d)) {
      z = Double.NaN;
    } else if (d < Stochastics.ICDF_P_LOW) {
      z = Math.sqrt(-2.0d * Math.log(d));
      z = ((((((((((Stochastics.ICDF_C[0] * z) + Stochastics.ICDF_C[1]) * z) + Stochastics.ICDF_C[2]) * z) + Stochastics.ICDF_C[3]) * z) + Stochastics.ICDF_C[4]) * z) + Stochastics.ICDF_C[5])
          / ((((((((Stochastics.ICDF_D[0] * z) + Stochastics.ICDF_D[1]) * z) + Stochastics.ICDF_D[2]) * z) + Stochastics.ICDF_D[3]) * z) + 1.0);
    } else if (Stochastics.ICDF_P_HIGH < d) {
      z = Math.sqrt(-2.0 * Math.log(1 - d));
      z = -((((((((((Stochastics.ICDF_C[0] * z) + Stochastics.ICDF_C[1]) * z) + Stochastics.ICDF_C[2]) * z) + Stochastics.ICDF_C[3]) * z) + Stochastics.ICDF_C[4]) * z) + Stochastics.ICDF_C[5])
          / ((((((((Stochastics.ICDF_D[0] * z) + Stochastics.ICDF_D[1]) * z) + Stochastics.ICDF_D[2]) * z) + Stochastics.ICDF_D[3]) * z) + 1.0d);
    } else {
      z = (d - 0.5d);
      r = (z * z);
      z = (((((((((((Stochastics.ICDF_A[0] * r) + Stochastics.ICDF_A[1]) * r) + Stochastics.ICDF_A[2]) * r) + Stochastics.ICDF_A[3]) * r) + Stochastics.ICDF_A[4]) * r) + Stochastics.ICDF_A[5]) * z)
          / ((((((((((Stochastics.ICDF_B[0] * r) + Stochastics.ICDF_B[1]) * r) + Stochastics.ICDF_B[2]) * r) + Stochastics.ICDF_B[3]) * r) + Stochastics.ICDF_B[4]) * r) + 1.0d);
    }

    /* TODO if((d > 0) && (d < 1)) { r = 0.5D erfc(-z/Math.sqrt(2.0D)) - d; d =
     * z Math.sqrt(2.0DMath.PI) Math.exp((zz)/2.0D); z = z - d/(1.0D +
     * zd/2.0D); } */
    return z;
  }

  /**
   * Computes the error function The code was taken from FORTRAN and translated
   * to Java. The next paragraph contains information from the original source.
   * [ The main computation evaluates near-minimax approximations from
   * "Rational Chebyshev approximations for the error function" by W. J. Cody,
   * Math. Comp., 1969, PP. 631-638. This transportable program uses rational
   * functions that theoretically approximate erf(x) and erfc(x) to at least 18
   * significant decimal digits. The accuracy achieved depends on the
   * arithmetic system, the compiler, the intrinsic functions, and proper
   * selection of the machine-dependent constants. Author: W. J. Cody
   * Mathematics and Computer Science Division Argonne National Laboratory
   * Argonne, IL 60439 ]
   *
   * @param x
   *          a real number
   * @param flag
   *          integer indicator
   * @return see below <code>flag==0</code>: computes the error function: y =
   *         2/pi * integral(0, x, exp(-t*t) dt) <code>flag==1</code> :
   *         computes the complementary error function: y = 2/pi * integral(x,
   *         infinite, exp(-t*t) dt) <code>flag==2</code>: computes the scaled
   *         complementary error function for large x: y = exp(x*x) * erfc(x) *
   *         1/(x*sqrt(pi))
   */
  private static double calerf(final double x, final int flag) {
    double result, y, y_sq, x_num, x_den, del;
    int i;

    result = 0.0d;
    y = Math.abs(x);

    if (y <= Stochastics.ERF_THRESHOLD) {
      y_sq = 0.0D;
      if (y > Mathematics.EPS) {
        y_sq = (y * y);
      }

      x_num = (Stochastics.ERF_A[4] * y_sq);
      x_den = y_sq;

      for (i = 0; i < 3; i++) {
        x_num = (x_num + Stochastics.ERF_A[i]) * y_sq;
        x_den = (x_den + Stochastics.ERF_B[i]) * y_sq;
      }

      result = (x * (x_num + Stochastics.ERF_A[3]))
          / (x_den + Stochastics.ERF_B[3]);
      if (flag != 0) {
        result = (1.0 - result);
      }
      if (flag == 2) {
        result = Math.exp(y_sq) * result;
      }
      return result;
    } else if (y <= 4.0d) {
      x_num = (Stochastics.ERF_C[8] * y);
      x_den = y;

      for (i = 0; i < 7; i++) {
        x_num = (x_num + Stochastics.ERF_C[i]) * y;
        x_den = (x_den + Stochastics.ERF_D[i]) * y;
      }

      result = (x_num + Stochastics.ERF_C[7]) / (x_den + Stochastics.ERF_D[7]);
      if (flag != 2) {
        y_sq = Math.round(y * 16.0D) / 16.0D;
        del = (y - y_sq) * (y + y_sq);
        result = Math.exp(-y_sq * y_sq) * Math.exp(-del) * result;
      }
    } else {
      result = 0.0d;

      if ((y < Stochastics.ERF_X_BIG)
          || ((flag == 2) && (y < Stochastics.ERF_X_MAX))) {
        if ((y >= Stochastics.ERF_X_BIG) && (y >= Stochastics.ERF_X_HUGE)) {
          result = Mathematics.SQRT_PI / y;
        } else {
          y_sq = 1.0D / (y * y);
          x_num = Stochastics.ERF_P[5] * y_sq;
          x_den = y_sq;

          for (i = 0; i < 4; i++) {
            x_num = (x_num + Stochastics.ERF_P[i]) * y_sq;
            x_den = (x_den + Stochastics.ERF_Q[i]) * y_sq;
          }

          result = (y_sq * (x_num + Stochastics.ERF_P[4]))
              / (x_den + Stochastics.ERF_Q[4]);
          result = (Mathematics.SQRT_PI - result) / y;

          if (flag != 2) {
            y_sq = Math.round(y * 16.0D) / 16.0D;
            del = (y - y_sq) * (y + y_sq);
            result = Math.exp(-y_sq * y_sq) * Math.exp(-del) * result;
          }
        }
      }
    }

    if (flag == 0) {
      result = (0.5d - result) + 0.5D;
      if (x < 0) {
        result = -result;
      }
    } else if (flag == 1) {
      if (x < 0) {
        result = 2.0D - result;
      }
    } else {
      if (x < 0) {
        if (x < Stochastics.ERF_X_NEG) {
          result = Double.MAX_VALUE;
        } else {
          y_sq = Math.round(x * 16.0D) / 16.0D;
          del = (x - y_sq) * (x + y_sq);
          y = Math.exp(y_sq * y_sq) * Math.exp(del);
          result = (y + y) - result;
        }
      }
    }
    return result;
  }

  /**
   * Compute the error function for the value <code>d</code>.
   *
   * @param d
   *          The parameter of the error function.
   * @return The value of the error function at <code>d</code>.
   */
  public static final double erf(final double d) {
    return Stochastics.calerf(d, 0);
  }

  /**
   * Compute the complementary error function for the value <code>d</code>.
   *
   * @param d
   *          The parameter of the complementary error function.
   * @return The value of the complementary error function at <code>d</code>.
   */
  public static final double erfc(final double d) {
    return Stochastics.calerf(d, 1);
  }

  /**
   * Compute the scaled complementary error function for the value
   * <code>d</code>.
   *
   * @param d
   *          The parameter of the scaled complementary error function.
   * @return The value of the scaled complementary error function at
   *         <code>d</code>. TODO: CHECK
   */
  public static final double erfcx(final double d) {
    return Stochastics.calerf(d, 2);
  }

  /**
   * Computes the cumulative of the normal distribution which is the
   * probability of d.
   *
   * @param d
   *          the x-value
   * @return 1/sqrt(2*pi) * integral(-infinit, d, exp(d*d/2))
   */
  public static final double normalCDF(final double d) {
    return 0.5d + (0.5d * Stochastics.erf(d / Mathematics.SQRT_2));
  }

  /**
   * Returns the probability density function of the normal distribution.
   *
   * @param d
   *          The position to get the probability density of.
   * @return The value of the probability density at position d.
   */
  public static final double normalPDF(final double d) {
    return (1.0d / Mathematics.SQRT_2_PI) * Math.exp(-0.5d * d * d);
  }

  /**
   * Computes the cumulative of the chi square distribution which is the
   * probability of d.
   *
   * @param x
   *          the x-value
   * @param dof
   *          the degrees of freedom
   * @return the probability of x TODO: CHECK
   */
  public static final double chiSquareCDF(final double x, final int dof) {
    double a, y, s, e, c, z, xx;
    boolean even;

    if ((x <= 0.0d) || (dof < 1)) {
      return 1.0d;
    }

    xx = x;
    a = 0.5 * xx;
    even = (dof & 1) == 0;

    if (dof > 1) {
      y = Math.exp(-a);
    } else {
      y = 0.0;
    }

    s = (even ? y : (2.0 * Stochastics.normalCDF(-Math.sqrt(xx))));

    if (dof > 2) {
      xx = 0.5 * (dof - 1.0);
      z = (even ? 1.0 : 0.5);

      if (a > (-Mathematics.LN_EPS)) {
        e = (even ? 0.0 : Mathematics.LN_SQRT_PI);
        c = Math.log(a);

        while (z <= xx) {
          e += Math.log(z);
          s += Math.exp((c * z) - a - e);
          z += 1.0;
        }

        return s;
      }

      e = (even ? 1.0 : (Mathematics.INV_SQRT_PI / Math.sqrt(a)));
      c = 0.0;

      while (z <= xx) {
        e *= (a / z);
        c += e;
        z += 1.0;
      }
      return (c * y) + s;
    }

    return s;
  }

  /**
   * Computes the cumulative of the chi square distribution which is the
   * probability of d.
   *
   * @param x
   *          the x-value
   * @param dof
   *          the degrees of freedom
   * @return the probability of x TODO: CHECK
   */
  public static final double chiSquareCompCDF(final double x, final int dof) {
    if ((x < 0.0d) || (dof < 1)) {
      return 0.0d;
    }

    return Stochastics.gammaRegularizedP(dof >> 1, x * 0.5d);
  }

  /**
   * Calculates the chi-square-quantil for the probability d with dof degrees
   * of freedom.
   *
   * @param d
   *          the probability (often called alpha)
   * @param dof
   *          the degrees of freedom
   * @return the quantile chi^2 (d, dof) TODO: CHECK
   */
  public static final double chiSquareQuantil(final double d, final int dof) {
    double max, min, x;

    if (d <= 0.0) {
      return Double.POSITIVE_INFINITY;
    }
    if (d >= 1.0) {
      return 0.0;
    }

    max = 1E4d;
    min = 0.0;

    for (;;) {
      x = 0.5 * (max + min);

      if (Stochastics.chiSquareCDF(x, dof) <= d) {
        if (max == x) {
          return x;
        }
        max = x;
      } else {
        if (min == x) {
          return x;
        }
        min = x;
      }
    }
  }

  /**
   * Returns the probability with that a random distribution with the
   * calculated chi-square-value d and dof degrees of freedom passes the
   * chi-square org.sfc.ztest.
   *
   * @param d
   *          chi-square value of a measurement series
   * @param dof
   *          degrees of freedom of this measurement series
   * @return the probability with that the series of measurements passes the
   *         chi-square org.sfc.ztest TODO: CHECK
   */
  public static final double chiSquarePass(final double d, final int dof) {
    double max, min, x;

    max = 1.0;
    min = 0.0;
    for (;;) {
      x = 0.5 * (max + min);
      if (Stochastics.chiSquareQuantil(x, dof) < d) {
        if (max == x) {
          return x;
        }
        max = x;
      } else {
        if (min == x) {
          return x;
        }
        min = x;
      }
    }
  }

  /**
   * Calculate the digamma-function. The digamma-function is often also refered
   * as "psi"-function.
   *
   * @param x
   *          The argument.
   * @return The digamma-function's value of <code>x</code>.
   */
  public static final double digamma(final double x) {
    double tn1, tn, resul, tnx, xx;
    int n;

    /* force into the interval 1..3 */
    if (x < 0.0d) {
      return Stochastics.digamma(1.0d - x) + Math.PI
          + Math.tan(Math.PI * (1.0d - x));
    }

    if (x < 1.0d) {
      return Stochastics.digamma(1.0d + x) - (1.0d / x);
    }

    if (Math.abs(x - 1.0d) <= Mathematics.EPS) {
      return -Mathematics.EULER_CONSTANT;
    }
    if (Math.abs(x - 2.0d) <= Mathematics.EPS) {
      return 1.0d - Mathematics.EULER_CONSTANT;
    }
    if (Math.abs(x - 3.0d) <= Mathematics.EPS) {
      return 1.5d - Mathematics.EULER_CONSTANT;
    }

    if (x > 3.0d) {
      return ((0.5d * (Stochastics.digamma(x * 0.5d) + Stochastics
          .digamma((x + 1.0d) * 0.5d))) + Mathematics.LN_2);
    }

    tn1 = 1.0d;
    tn = (x - 2.0d);
    resul = Stochastics.DIGAMMA_KNOCE[0] + (Stochastics.DIGAMMA_KNOCE[1] * tn);

    xx = (x - 2.0d);

    for (n = 2; n < Stochastics.DIGAMMA_KNOCE.length; n++) {
      tnx = (2.0d * xx * tn) - tn1;
      resul += Stochastics.DIGAMMA_KNOCE[n] * tnx;
      tn1 = tn;
      tn = tnx;
    }

    return resul;
  }

  /**
   * Compute the Bessel function of order 0 of the argument.
   *
   * @param x
   *          a double value
   * @return The Bessel function of order 0 of the argument. TODO: CHECK
   */

  public static final double j0(final double x) {
    double ax, y, z, xx;

    if ((ax = Math.abs(x)) < 8.0d) {
      y = (x * x);

      return (57568490574.0d + (y * (-13362590354.0d + (y * (651619640.7d + (y * (-11214424.18 + (y * (77392.33017 + (y * (-184.9052456)))))))))))
          / (57568490411.0d + (y * (1029532985.0d + (y * (9494680.718d + (y * (59272.64853d + (y * (267.8532712d + (y * 1.0))))))))));
    }

    z = 8.0d / ax;
    y = z * z;
    xx = ax - 0.785398164d;

    return Math.sqrt(0.636619772d / ax)
        * ((Math.cos(xx) * (1.0d + (y * (-0.1098628627e-2d + (y * (0.2734510407e-4d + (y * (-0.2073370639e-5d + (y * 0.2093887211e-6d))))))))) - (z
            * Math.sin(xx) * (-0.1562499995e-1d + (y * (0.1430488765e-3d + (y * (-0.6911147651e-5d + (y * (0.7621095161e-6d - (y * 0.934935152e-7d))))))))));
  }

  /**
   * Compute the Bessel function of order 1 of the argument.
   *
   * @param x
   *          a double value
   * @return The Bessel function of order 1 of the argument. TODO: CHECK
   */
  public static final double j1(final double x) {
    double ax, y, z, xx;

    if ((ax = Math.abs(x)) < 8.0d) {
      y = (x * x);

      return (x * (72362614232.0d + (y * (-7895059235.0d + (y * (242396853.1d + (y * (-2972611.439d + (y * (15704.48260d + (y * (-30.16036606d))))))))))))
          / (144725228442.0d + (y * (2300535178.0d + (y * (18583304.74d + (y * (99447.43394d + (y * (376.9991397d + (y * 1.0d))))))))));
    }

    z = 8.0d / ax;
    xx = ax - 2.356194491d;
    y = z * z;

    y = Math.sqrt(0.636619772 / ax)
        * ((Math.cos(xx) * ((1.0d + (y * (0.183105e-2d + (y * (-0.3516396496e-4d + (y * (0.2457520174e-5d + (y * (-0.240337019e-6d))))))))))) - (z
            * Math.sin(xx) * (0.04687499995d + (y * (-0.2002690873e-3d + (y * (0.8449199096e-5d + (y * (-0.88228987e-6d + (y * 0.105787412e-6d))))))))));

    if (x < 0.0d) {
      return -y;
    }
    return y;
  }

  /**
   * Compute the Bessel function of order n of the argument.
   *
   * @param n
   *          integer order
   * @param x
   *          a double value
   * @return The Bessel function of order n of the argument. TODO: CHECK
   */
  public static final double jn(final int n, final double x) {
    int j, m;
    double ax, bj, bjm, bjp, sum, tox, ans;
    boolean jsum;

    if (n == 0) {
      return Stochastics.j0(x);
    }
    if (n == 1) {
      return Stochastics.j1(x);
    }

    ax = Math.abs(x);

    if (ax == 0.0) {
      return 0.0d;
    }

    tox = (2.0 / ax);

    if (ax > n) {
      bjm = Stochastics.j0(ax);
      bj = Stochastics.j1(ax);

      for (j = 1; j < n; j++) {
        bjp = (j * tox * bj) - bjm;
        bjm = bj;
        bj = bjp;
      }
      ans = bj;
    } else {
      m = (((n + ((int) (Math.sqrt(Stochastics.BESSEL_ACC * n)))) >> 1)) << 1;
      jsum = false;
      bjp = ans = sum = 0.0d;
      bj = 1.0d;

      for (j = m; j > 0; j--) {
        bjm = (j * tox * bj) - bjp;
        bjp = bj;
        bj = bjm;

        if (Math.abs(bj) > Stochastics.BESSEL_BIGNO) {
          bj *= Stochastics.BESSEL_BIGNI;
          bjp *= Stochastics.BESSEL_BIGNI;
          ans *= Stochastics.BESSEL_BIGNI;
          sum *= Stochastics.BESSEL_BIGNI;
        }

        if (jsum) {
          sum += bj;
        }

        jsum = !jsum;
        if (j == n) {
          ans = bjp;
        }
      }

      sum = (2.0d * sum) - bj;
      ans /= sum;
    }
    return ((x < 0.0d) && ((n % 2) == 1)) ? -ans : ans;
  }

  /**
   * Compute the Bessel function of the second kind, of order 0 of the
   * argument.
   *
   * @param x
   *          a double value
   * @return The Bessel function of the second kind, of order 0 of the
   *         argument. TODO: CHECK
   */

  public final static double y0(final double x) {
    double y, z, xx;

    if (x < 8.0d) {
      y = (x * x);
      return ((-2957821389.0d + (y * (7062834065.0d + (y * (-512359803.6d + (y * (10879881.29d + (y * (-86327.92757d + (y * 228.4622733)))))))))) / (40076544269.0d + (y * (745249964.8d + (y * (7189466.438d + (y * (47447.26470d + (y * (226.1030244d + (y * 1.0d)))))))))))
          + (0.636619772d * Stochastics.j0(x) * Math.log(x));
    }

    z = 8.0d / x;
    y = z * z;
    xx = x - 0.785398164d;

    return Math.sqrt(0.636619772d / x)
        * ((Math.sin(xx) * (1.0d + (y * (-0.1098628627e-2d + (y * (0.2734510407e-4d + (y * (-0.2073370639e-5d + (y * 0.2093887211e-6d))))))))) + (z
            * Math.cos(xx) * (-0.1562499995e-1d + (y * (0.1430488765e-3d + (y * (-0.6911147651e-5d + (y * (0.7621095161e-6d + (y * (-0.934945152e-7d)))))))))));
  }

  /**
   * Compute the Bessel function of the second kind, of order 1 of the
   * argument.
   *
   * @param x
   *          a double value
   * @return The Bessel function of the second kind, of order 1 of the
   *         argument. TODO: CHECK
   */
  public static final double y1(final double x) {
    double y, z, xx;

    if (x < 8.0d) {
      y = (x * x);
      return ((x * (-0.4900604943e13d + (y * (0.1275274390e13d + (y * (-0.5153438139e11d + (y * (0.7349264551e9d + (y * (-0.4237922726e7d + (y * 0.8511937935e4d))))))))))) / (0.2499580570e14d + (y * (0.4244419664e12d + (y * (0.3733650367e10d + (y * (0.2245904002e8d + (y * (0.1020426050e6d + (y * (0.3549632885e3d + y))))))))))))
          + (0.636619772d * ((Stochastics.j1(x) * Math.log(x)) - (1.0 / x)));
    }

    z = 8.0d / x;
    y = z * z;
    xx = x - 2.356194491d;

    return Math.sqrt(0.636619772d / x)
        * ((Math.sin(xx) * (1.0d + (y * (0.183105e-2d + (y * (-0.3516396496e-4d + (y * (0.2457520174e-5d + (y * (-0.240337019e-6d)))))))))) + (z
            * Math.cos(xx) * (0.04687499995d + (y * (-0.2002690873e-3d + (y * (0.8449199096e-5d + (y * (-0.88228987e-6d + (y * 0.105787412e-6d))))))))));
  }

  /**
   * Compute the Bessel function of the second kind, of order n of the
   * argument.
   *
   * @param n
   *          integer order
   * @param x
   *          a double value
   * @return The Bessel function of the second kind, of order n of the
   *         argument. TODO: CHECK
   */
  public static final double yn(final int n, final double x) {
    double by, bym, byp, tox, j;

    if (n == 0) {
      return Stochastics.y0(x);
    }
    if (n == 1) {
      return Stochastics.y1(x);
    }

    tox = (2.0d / x);
    by = Stochastics.y1(x);
    bym = Stochastics.y0(x);

    for (j = 1; j < n; j++) {
      byp = (j * tox * by) - bym;
      bym = by;
      by = byp;
    }

    return by;
  }

  /**
   * Returns the sum of the first k terms of the Poisson distribution.
   *
   * @param k
   *          number of terms
   * @param x
   *          double value
   * @return The sum of the first k terms of the Poisson distribution. TODO:
   *         CHECK
   */

  public static final double poissonCDF(final int k, final double x) {
    if ((k < 0) || (x < 0.0d)) {
      return 0.0d;
    }

    return Stochastics.gammaRegularizedQ(k + 1, x);
  }

  /**
   * Returns the sum of the terms k+1 to infinity of the Poisson distribution.
   *
   * @param k
   *          start
   * @param x
   *          double value
   * @return The sum of the terms k+1 to infinity of the Poisson distribution.
   *         TODO: CHECK
   */

  public static final double poissonCompCDF(final int k, final double x) {
    if ((k < 0) || (x < 0.0d)) {
      return 0.0d;
    }

    return Stochastics.gammaRegularizedP(k + 1, x);
  }

  /**
   * Calculates the beta function.
   *
   * @param z
   *          The first (z) parameter of the beta function.
   * @param w
   *          The second (w) parameter of the beta function.
   * @return The value of the beta function <code>beta(z, w)</code>. TODO:
   *         CHECK
   */
  public static final double beta(final double z, final double w) {
    return Math.exp((Stochastics.gammaLn(z) + Stochastics.gammaLn(w))
        - Stochastics.gammaLn(z + w));
  }

  /**
   * Compute the Incomplete Beta Function evaluated from zero to xx.
   *
   * @param aa
   *          double value
   * @param bb
   *          double value
   * @param xx
   *          double value
   * @return The Incomplete Beta Function evaluated from zero to xx. TODO:
   *         CHECK
   */
  public static final double betaIncomplete(final double aa, final double bb,
      final double xx) {
    double a, b, t, x, xc, w, y;
    boolean flag;

    flag = false;

    if (((bb * xx) <= 1.0d) && (xx <= 0.95)) {
      return Stochastics.pseries(aa, bb, xx);
    }

    w = (1.0d - xx);

    if (xx > (aa / (aa + bb))) {
      flag = true;
      a = bb;
      b = aa;
      xc = xx;
      x = w;
    } else {
      a = aa;
      b = bb;
      xc = w;
      x = xx;
    }

    if (flag && ((b * x) <= 1.0) && (x <= 0.95)) {
      t = Stochastics.pseries(a, b, x);

      if (t <= Mathematics.EPS) {
        t = 1.0d - Mathematics.EPS;
      } else {
        t = 1.0 - t;
      }

      return t;
    }

    y = (x * ((a + b) - 2.0d)) - (a - 1.0);

    if (y < 0.0d) {
      w = Stochastics.incbcf(a, b, x);
    } else {
      w = Stochastics.incbd(a, b, x) / xc;
    }

    y = a * Math.log(x);
    t = b * Math.log(xc);

    if (((a + b) < Stochastics.MAX_GAMMA)
        && (Math.abs(y) < Stochastics.LN_MAX_DOUBLE)
        && (Math.abs(t) < Stochastics.LN_MAX_DOUBLE)) {
      t = Math.pow(xc, b);
      t *= Math.pow(x, a);
      t /= a;
      t *= w;
      t *= Stochastics.gamma(a + b)
          / (Stochastics.gamma(a) * Stochastics.gamma(b));

      if (flag) {
        if (t <= Mathematics.EPS) {
          t = 1.0d - Mathematics.EPS;
        } else {
          t = 1.0 - t;
        }
      }
      return t;
    }

    y += (t + Stochastics.gammaLn(a + b)) - Stochastics.gammaLn(a)
        - Stochastics.gamma(b);
    y += Math.log(w / a);

    if (y < Stochastics.LN_MIN_DOUBLE) {
      t = 0.0;
    } else {
      t = Math.exp(y);
    }

    if (flag) {
      if (t <= Mathematics.EPS) {
        t = 1.0d - Mathematics.EPS;
      } else {
        t = 1.0d - t;
      }
    }

    return t;
  }

  /**
   * Continued fraction expansion #1 for incomplete beta integral.
   *
   * @param a
   *          double value
   * @param b
   *          double value
   * @param x
   *          double value
   * @return The Incomplete Beta Function evaluated from zero to xx.
   */
  private static final double incbcf(final double a, final double b,
      final double x) {
    double xk, pk, pkm1, pkm2, qk, qkm1, qkm2, k1, k2, k3, k4, k5, k6, k7, k8, r, t, ans, thresh;

    int n;

    k1 = a;
    k2 = (a + b);
    k3 = a;
    k4 = (a + 1.0d);
    k5 = 1.0d;
    k6 = (b - 1.0d);
    k7 = k4;
    k8 = (a + 2.0);

    pkm2 = 0.0d;
    qkm2 = 1.0d;
    pkm1 = 1.0d;
    qkm1 = 1.0d;
    ans = 1.0d;
    r = 1.0;
    n = 0;

    thresh = 3.0 * Mathematics.EPS;

    do {
      xk = -(x * k1 * k2) / (k3 * k4);
      pk = pkm1 + (pkm2 * xk);
      qk = qkm1 + (qkm2 * xk);
      pkm2 = pkm1;
      pkm1 = pk;
      qkm2 = qkm1;
      qkm1 = qk;

      xk = (x * k5 * k6) / (k7 * k8);
      pk = pkm1 + (pkm2 * xk);
      qk = qkm1 + (qkm2 * xk);
      pkm2 = pkm1;
      pkm1 = pk;
      qkm2 = qkm1;
      qkm1 = qk;

      if (qk != 0) {
        r = pk / qk;
      }

      if (r != 0) {
        t = Math.abs((ans - r) / r);
        ans = r;
      } else {
        t = 1.0d;
      }

      if (t < thresh) {
        return ans;
      }

      k1 += 1.0d;
      k2 += 1.0d;
      k3 += 2.0d;
      k4 += 2.0d;
      k5 += 1.0d;
      k6 -= 1.0d;
      k7 += 2.0d;
      k8 += 2.0d;

      if ((Math.abs(qk) + Math.abs(pk)) > Stochastics.ICG_BIG) {
        pkm2 *= Stochastics.ICG_BIGINV;
        pkm1 *= Stochastics.ICG_BIGINV;
        qkm2 *= Stochastics.ICG_BIGINV;
        qkm1 *= Stochastics.ICG_BIGINV;
      }

      if ((Math.abs(qk) < Stochastics.ICG_BIG)
          || (Math.abs(pk) < Stochastics.ICG_BIGINV)) {
        pkm2 *= Stochastics.ICG_BIG;
        pkm1 *= Stochastics.ICG_BIG;
        qkm2 *= Stochastics.ICG_BIG;
        qkm1 *= Stochastics.ICG_BIG;
      }
    } while ((++n) < 300);

    return ans;
  }

  /**
   * Continued fraction expansion #2 for incomplete beta integral
   *
   * @param a
   *          double value
   * @param b
   *          double value
   * @param x
   *          double value
   * @return The Incomplete Beta Function evaluated from zero to xx.
   */
  private static final double incbd(final double a, final double b,
      final double x) {
    double xk, pk, pkm1, pkm2, qk, qkm1, qkm2, k1, k2, k3, k4, k5, k6, k7, k8, r, t, ans, z, thresh;
    int n;

    k1 = a;
    k2 = b - 1.0d;
    k3 = a;
    k4 = a + 1.0d;
    k5 = 1.0d;
    k6 = a + b;
    k7 = a + 1.0d;
    k8 = a + 2.0d;

    pkm2 = 0.0;
    qkm2 = 1.0;
    pkm1 = 1.0;
    qkm1 = 1.0;
    z = x / (1.0d - x);
    ans = 1.0;
    r = 1.0;
    n = 0;

    thresh = 3.0 * Mathematics.EPS;

    do {
      xk = -(z * k1 * k2) / (k3 * k4);
      pk = pkm1 + (pkm2 * xk);
      qk = qkm1 + (qkm2 * xk);
      pkm2 = pkm1;
      pkm1 = pk;
      qkm2 = qkm1;
      qkm1 = qk;

      xk = (z * k5 * k6) / (k7 * k8);
      pk = pkm1 + (pkm2 * xk);
      qk = qkm1 + (qkm2 * xk);
      pkm2 = pkm1;
      pkm1 = pk;
      qkm2 = qkm1;
      qkm1 = qk;

      if (qk != 0) {
        r = pk / qk;
      }

      if (r != 0) {
        t = Math.abs((ans - r) / r);
        ans = r;
      } else {
        t = 1.0d;
      }

      if (t < thresh) {
        return ans;
      }

      k1 += 1.0;
      k2 -= 1.0;
      k3 += 2.0;
      k4 += 2.0;
      k5 += 1.0;
      k6 += 1.0;
      k7 += 2.0;
      k8 += 2.0;

      if ((Math.abs(qk) + Math.abs(pk)) > Stochastics.ICG_BIG) {
        pkm2 *= Stochastics.ICG_BIGINV;
        pkm1 *= Stochastics.ICG_BIGINV;
        qkm2 *= Stochastics.ICG_BIGINV;
        qkm1 *= Stochastics.ICG_BIGINV;
      }

      if ((Math.abs(qk) < Stochastics.ICG_BIGINV)
          || (Math.abs(pk) < Stochastics.ICG_BIGINV)) {
        pkm2 *= Stochastics.ICG_BIG;
        pkm1 *= Stochastics.ICG_BIG;
        qkm2 *= Stochastics.ICG_BIG;
        qkm1 *= Stochastics.ICG_BIG;
      }
    } while (++n < 300);

    return ans;
  }

  /**
   * Power series for incomplete beta integral. Use when b*x is small and x not
   * too close to 1.
   *
   * @param a
   *          double value
   * @param b
   *          double value
   * @param x
   *          double value
   * @return The Incomplete Beta Function evaluated from zero to xx.
   */
  private static final double pseries(final double a, final double b,
      final double x) {
    double s, t, u, v, n, t1, z, ai;

    ai = (1.0d / a);
    u = (1.0d - b) * x;
    v = u / (a + 1.0);
    t1 = v;
    t = u;
    n = 2.0d;
    s = 0.0d;
    z = Mathematics.EPS * ai;

    while (Math.abs(v) > z) {
      u = ((n - b) * x) / n;
      t *= u;
      v = t / (a + n);
      s += v;
      n += 1.0d;
    }

    s += t1;
    s += ai;

    u = a * Math.log(x);

    if (((a + b) < Stochastics.MAX_GAMMA)
        && (Math.abs(u) < Stochastics.LN_MAX_DOUBLE)) {
      t = Stochastics.gamma(a + b)
          / (Stochastics.gamma(a) * Stochastics.gamma(b));
      s = s * t * Math.pow(x, a);
    } else {
      t = (Stochastics.gammaLn(a + b) - Stochastics.gammaLn(a) - Stochastics
          .gammaLn(b)) + u + Math.log(s);

      if (t < Stochastics.LN_MIN_DOUBLE) {
        s = 0.0d;
      } else {
        s = Math.exp(t);
      }
    }

    return s;
  }

  /**
   * Calculate the <code>n</code>'th harmonic number.
   *
   * @param n
   *          The index of the harmonic number to be created.
   * @return The <code>n</code>'th harmonic number. TODO: improve code - it's
   *         really weak
   */
  public static final double harmonic(final int n) {
    double sum;
    int i;

    sum = 1.0d;
    for (i = n; i > 1; i--) {
      sum += (1.0d / i);
    }

    return sum;
  }

}
