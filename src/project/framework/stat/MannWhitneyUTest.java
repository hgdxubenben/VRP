package project.framework.stat;

import java.util.Arrays;

/**
 * The Mann-Whitney U Test
 *
 * @author Thomas Weise
 */
public final class MannWhitneyUTest {

  /** The globally shared instance of the mann-whitney u test */
  public static final MannWhitneyUTest MANN_WHITNEY_U = new MannWhitneyUTest();

  /**
   * The internal constructor
   */
  private MannWhitneyUTest() {
    super();
  }

  /**
   * Compute the test for given data sources. In the result, the test
   * statistics are the mean ranks.
   *
   * @param da
   *          the first data source
   * @param db
   *          the second data source
   * @param twoTailed
   *          <code>true</code> if and only if the two-tailed version of the
   *          test should be performed, <code>false</code> if the single-tailed
   *          version is required
   * @return the test result
   */
  public final BivariateTestResult test(final double[] da, final double[] db,
      final boolean twoTailed) {

    final double[] srt;
    final double[] ranks;
    final int l1, l2, l;
    double rs1, rs2, u, d, p;
    int i, j;

    l1 = da.length;
    l2 = db.length;
    l = (l1 + l2);
    srt = new double[l];
    System.arraycopy(da, 0, srt, 0, l1);
    System.arraycopy(db, 0, srt, l1, l2);
    Arrays.sort(srt);

    ranks = new double[l];
    i = 0;
    while (i < l) {
      j = i;
      d = srt[i];
      while ((j < l) && (Double.compare(d, srt[j]) == 0)) {
        j++;
      }

      Arrays.fill(ranks, i, j, (i + (((j - i) + 1) * 0.5)));
      i = j;
    }

    rs1 = 0l;
    for (i = (l1 - 1); i >= 0; i--) {
      j = Arrays.binarySearch(srt, da[i]);
      rs1 += ranks[j];
    }

    // rs2 = 0l;
    // for (i = (l2 - 1); i >= 0; i--) {
    // j = Arrays.binarySearch(srt, db[i]);
    // rs2 += ranks[j];
    // }
    rs2 = (((0.5d * l) * (l + 1)) - rs1);
    u = Math.min(rs1 - ((0.5d * l1) * (l1 + 1)), rs2
        - ((0.5d * l2) * (l2 + 1)));

    p = 1d - Stochastics.normalCDF((((0.5d * l1) * l2) - u)
        / Math.sqrt((((1d / 12d) * l1) * l2) * (l + 1)));

    return new BivariateTestResult(//
        rs1 / l1,//
        rs2 / l2,//
        (twoTailed ? (p + p) : p));
  }

  /**
   * The read resolve method.
   *
   * @return the object instance to be written
   */
  private final Object readResolve() {
    return MannWhitneyUTest.MANN_WHITNEY_U;
  }

  /**
   * The write replace method.
   *
   * @return the comparator instance to be written
   */
  private final Object writeReplace() {
    return MannWhitneyUTest.MANN_WHITNEY_U;
  }
}
