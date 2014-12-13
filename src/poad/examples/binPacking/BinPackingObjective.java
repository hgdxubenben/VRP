// start
package poad.examples.binPacking;

import poad.IObjectiveFunction;

/**
 * an objective function for the bin packing problem with permuation-based solution candidates
 */
// end
public class BinPackingObjective implements IObjectiveFunction<int[]> { // start

  /** an example problem */
  public static final BinPackingObjective EXAMPLE_PROBLEM = //
  new BinPackingObjective(100000,//
      new int[] {
          34991, 34941, 34922, 34866, 34849, 34771, 34768, 34748, 34544, 34358, 34254, 34155,
          34098, 34076, 34055, 34048, 34029, 33990, 33871, 33780, 33750, 33654, 33612, 33581,
          33430, 33260, 33197, 33155, 33115, 33007, 32989, 32795, 32708, 32394, 32384, 32309,
          32193, 32039, 32038, 32008, 31995, 31961, 31946, 31865, 31839, 31829, 31692, 31633,
          31354, 31169, 31141, 31006, 30929, 30843, 30842, 30807, 30741, 30514, 30395, 30387,
          30341, 30296, 30287, 30284, 30140, 30135, 30063, 29975, 29933, 29859, 29735, 29730,
          29703, 29525, 29518, 29423, 29378, 29234, 29218, 29178, 29092, 29089, 28947, 28647,
          28574, 28550, 28547, 28471, 28461, 28299, 28267, 28252, 28251, 28159, 28009, 28003,
          27967, 27852, 27811, 27664, 27508, 27413, 27409, 27184, 27162, 27113, 27099, 27048,
          27041, 26733, 26506, 26362, 26183, 25997, 25976, 25897, 25856, 25784, 25700, 25668,
          25641, 25522, 25490, 25433, 25408, 25322, 25299, 25237, 25091, 25057, 25015, 24990,
          24974, 24939, 24834, 24777, 24743, 24625, 24555, 24449, 24367, 24340, 24329, 24126,
          24085, 24050, 24020, 23999, 23989, 23974, 23928, 23837, 23836, 23565, 23491, 23422,
          23417, 23205, 23195, 23156, 23092, 22712, 22644, 22417, 22392, 22281, 22239, 22212,
          22067, 22045, 22042, 22003, 21866, 21851, 21849, 21713, 21674, 21608, 21607, 21594,
          21401, 21296, 21239, 21180, 21128, 21059, 20954, 20948, 20947, 20813, 20755, 20725,
          20693, 20585, 20513, 20431, 20338, 20310, 20296, 20081, }); // end

  /** the size of the bins */
  public final int binSize;

  /** the sizes of the objects */
  public final int[] objects; // start

  /**
   * instantiate
   * 
   * @param bs
   *          the bin size
   * @param o
   *          the object sizes
   */
  public BinPackingObjective(final int bs, final int[] o) {
    super();
    this.binSize = bs;
    this.objects = o;
  }

  /** {@inheritDoc} */
  // end
  @Override
  public double compute(final int[] x) {
    int bins, remainingSize, curSize;

    bins          = 0;                // assume zero bins
    remainingSize = 0;                // then there also is no space left in them

    for (final int i : x) {           // iterate over all elements in the permutation
      curSize = this.objects[i];      // get size of current element
      if (curSize > remainingSize) {  // if element does not fit in current bin anymore
        bins++; // open a new bin
        remainingSize = this.binSize; // remaining space = bin size
      }
      remainingSize  -= curSize;      // put object in bin: remaining size reduced
    }
    return bins;                      // return total number of bins required
  }
}
