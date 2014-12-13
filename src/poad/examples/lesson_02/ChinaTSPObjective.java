// start
package poad.examples.lesson_02;

import poad.IObjectiveFunction;

/**
 * An objective function for the tsp problem over five Chinese cities. Problem space: int[4]
 * encoding a permutation of the 4 cities where 0 stands for Hefei [omitted in solutions], 1 stands
 * for Shanghai 2 stands for Guangzhou, 3 stands for Chengdu, and 4 stands for Beijing.
 * China.dist(i, j) be a function returning the geographical distance between city i and city j
 */
// end
public final class ChinaTSPObjective implements IObjectiveFunction<int[]> {
  // start

  /** the index of hefei */
  public static final int HEFEI = 0;

  /** the index of shanghai */
  public static final int SHANGHAI = (ChinaTSPObjective.HEFEI + 1);

  /** the index of guangzhou */
  public static final int GUANGZHOU = (ChinaTSPObjective.SHANGHAI + 1);

  /** the index of chengdu */
  public static final int CHENGDU = (ChinaTSPObjective.GUANGZHOU + 1);

  /** the index of beijing */
  public static final int BEIJING = (ChinaTSPObjective.CHENGDU + 1);

  /** the number of cities */
  public static final int CITY_COUNT = (ChinaTSPObjective.BEIJING + 1);

  /** the internal array with the distances */
  private static final double[][] MATRIX = new double[][] {
      {
          427d, 1257d, 1615d, 1044d }, {
          1529d, 2095d, 1244d }, {
          1954d, 2174d }, {
        1854d }, };

  /** the city names */
  private static final String[] CITIES = new String[] {
      "Hefei", "Shanghai", "Guangzhou", "Chengdu", "Beijing" };

  /**
   * compute the distance between city a and city b
   * 
   * @param a
   *          the first city
   * @param b
   *          the second city
   * @return the distance between the cities
   */
  public static final double distance(final int a, final int b) {
    if (a == b) {
      return 0d;
    }
    if (a < b) {
      return ChinaTSPObjective.MATRIX[b][a];
    }
    return ChinaTSPObjective.MATRIX[a][b];
  }

  /**
   * get the city name
   * 
   * @param city
   *          the city
   * @return the name of that city
   */
  public static final String cityName(final int city) {
    return ChinaTSPObjective.CITIES[city];
  }

  /**
   * @param x
   *          the sequence of city indices (city (Hefei=0), is omitted: we start there)
   * @return the difference of the throw distance and 100m
   */
  @Override
  // end
  public final double compute(final int[] x) {
    double dist;

    dist = ChinaTSPObjective.distance(ChinaTSPObjective.HEFEI, x[0]);

    for (int i = 1; i < x.length; i++) {
      dist += ChinaTSPObjective.distance(x[i - 1], x[i]);
    }

    return (dist + ChinaTSPObjective.distance(x[x.length - 1], ChinaTSPObjective.HEFEI));
  }
}
