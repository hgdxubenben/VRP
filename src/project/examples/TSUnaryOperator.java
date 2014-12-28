package project.examples;

import java.util.Arrays;
import java.util.Random;

import poad.INullarySearchOperation;
import poad.IUnarySearchOperation;
import project.framework.Solution;

/**
 * This operator modifies an existing solution by swapping two orders.
 * 
 * @param <G>
 */
@SuppressWarnings("unused")
public class TSUnaryOperator<G> implements IUnarySearchOperation<Solution> {

  private static int[] carCapacity;
  private static int carNum;
  private static int orderNum;
  private static int[] orderWeight;
  private static int N;
  private static int[] initSolutionArray;
  private static int[] availCapacity;
  private static int[][] orderMap;
  private static int[] initLocaton;
  private static TabuList tabuList;
  private static Solution initSolution;
  private static int orderMovS, orderMovD, pos1, pos2, newpos1, newpos2;
  private static int iterator = 0;

  public TSUnaryOperator() {
    super();
    ExampleNullaryOperator nullary = new ExampleNullaryOperator();
    TSUnaryOperator.carCapacity = ExampleNullaryOperator.scenario.getAllCarCapacity();
    TSUnaryOperator.carNum = ExampleNullaryOperator.scenario.carCount();
    TSUnaryOperator.orderNum = ExampleNullaryOperator.scenario.orderCount();
    TSUnaryOperator.N = 2 * orderNum + carNum + 1;

    TSUnaryOperator.orderWeight = ExampleNullaryOperator.scenario.getAllOrderWeight();
    TSUnaryOperator.initSolutionArray = nullary.getStartSolutionArray();
    TSUnaryOperator.availCapacity = nullary.getStartAvailCapacity();
    TSUnaryOperator.orderMap = nullary.getStartOrderMap();
    TSUnaryOperator.initLocaton = nullary.getStartLocation();
    TSUnaryOperator.initSolution = nullary.getStartSolution();
    TSUnaryOperator.tabuList = new TabuList();

    int[] aa = new int[30];
    for (int i = 0; i < 30; i++)
      aa[i] = i;
    System.out.println(Arrays.toString(aa));

  }

  /**
   * create a new solution by swapping two orders in an existing one
   * 
   * @param parent
   *          the parent solution
   * @param r
   *          than randomizer
   */
  @Override
  public Solution mutate(Solution parent) {

    iterator++;
    return getBestNeighbour();
  }

  @SuppressWarnings("boxing")
  private Solution getBestNeighbour() {
    //System.out.println(orderMovS + " " + pos1 + " " + pos2 + " " + newpos1 + " " + newpos2);
   // System.out.println(Arrays.toString(initSolutionArray));
    int i, j, k, p, s, d;
    int curBenefit = 0, bestBenefit = Integer.MIN_VALUE;
    Boolean flag1 = true;
    Boolean flag2 = true;

    // SPI && WRI
    // one bug here: WRI ,the load available check should be changed
    for (i = 0; i < orderNum; ++i) {
      s = orderMap[i][0];
      d = orderMap[i][1];
      if (s == d) {
        System.out.println("======"+i+" "+s+" "+d);
      }
      for (j = 1; j < N-2; ++j) {
        if(j>N-5){
          int ii=0;
        }
        if (j >= s - 1 && j < d) j = d + 1;
        for (k = j + 1; k < N-2; ++k) {
          if (j < s && k == s - 1) break;

          if (initSolutionArray[k] < orderNum || j==k-1) {
            // check whether the car can load the new insert order
            if (availCapacity[k - 1] < orderWeight[i]) {
              if (k >= orderMap[i][0] && k < orderMap[i][1]) {
                flag2 = true;
              } else flag2 = false;
            } else {

              if (tabuList.shouldTabu(i, j, orderMap[i][0], iterator)
                  || tabuList.shouldTabu(i + orderNum, k, orderMap[i][1], iterator)) continue;
              curBenefit = calculateProfit(s, d, j, k);
              if (curBenefit > bestBenefit) {
                bestBenefit = curBenefit;
                pos1 = s;
                pos2 = d;
                newpos1 = j;
                newpos2 = k;
                orderMovS = i;
              }
            }
          } else {
            flag1 = false;
            break;
          }
          if (flag2 == false) break;
        }
        
        flag1 = true;
        flag2 = true;

      }
    }
    
    update();

    int[][] returnResult = new int[carNum][];
    for (i = 0; i < carNum; ++i) {
      int carStart = orderMap[orderNum + i ][0];
      int nextCarStart = orderMap[orderNum + i  + 1][0];
      int len = nextCarStart - carStart - 1;
      if (len == 0) continue;
      int[] result = new int[len];
      for (j = 0; j < len; ++j) {
        result[j] = initSolutionArray[carStart + 1 + j];
      }
      returnResult[i] = result;
    }
    
 //   System.out.println("Best benefit: "+bestBenefit);
    return new Solution(returnResult);
  }

  private void update() {
    tabuList.tabuMove(orderMovS, newpos1, orderMap[orderMovS][0], iterator);
    // ltabuList.tabuMove(orderMovS + orderNUm, newpos2, orderMap[orderMovS][1]);
    if (newpos1 == pos1 + 1) {
      tabuList.tabuMove2(initSolutionArray[newpos1], newpos1, iterator);
    }

    // construct the new solution
    int min = pos1 < newpos1 ? pos1 : newpos1;
    int max = pos2 > newpos2 ? pos2 : newpos2;
    int[] tempSolution = new int[max + 1];
    int[] tempLocation = new int[max + 1];
    int[] tempAvailCapciy = new int[max + 1];
    int i = 0, j = 0;
    int dif = 0;
    int pos1Location, pos2Location, pos1Order;

    pos1Location = initLocaton[pos1];
    pos2Location = initLocaton[pos2];
    pos1Order = initSolutionArray[pos1];
    if (pos1 < newpos1) {
      for (i = min; i <= max; i++) {
        if (i >= pos2 - 1 && i <= newpos1 - 1) dif = 2;
        else if (i == newpos1) {
          initLocaton[i] = pos1Location;
          initSolutionArray[i] = pos1Order;
          continue;
        } else if (i == newpos2) {
          initLocaton[i] = pos2Location;
          initSolutionArray[i] = pos1Order;
          continue;
        } else dif = 1;

        initLocaton[i] = initLocaton[i + dif];
        initSolutionArray[i] = initSolutionArray[i + dif];
      }
    } else {
      for (i = max; i >= min; i--) {
        if (i <= pos1 + 1 && i >= newpos2 + 1) dif = -2;
        else if (i == newpos1) {
          initLocaton[i] = pos1Location;
          initSolutionArray[i] = pos1Order;
          continue;
        } else if (i == newpos2) {
          initLocaton[i] = pos2Location;
          initSolutionArray[i] = pos1Order;
          continue;
        } else dif = -1;

        initLocaton[i] = initLocaton[i + dif];
        initSolutionArray[i] = initSolutionArray[i + dif];
      }
    }

    for (i = min; i <= max; i++) {
      if (initSolutionArray[i] > orderNum) {
        if (pos1 < newpos1) dif = -2;
        else dif = 2;
        orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + dif;
        continue;
      }

      if (  orderMap[initSolutionArray[i]][0] >= min && orderMap[initSolutionArray[i]][5] !=1) {
        availCapacity[i] = availCapacity[i - 1] - orderWeight[initSolutionArray[i]];
        orderMap[initSolutionArray[i]][3] = orderMap[initSolutionArray[i]][3] + i
            - orderMap[initSolutionArray[i]][0];
        orderMap[initSolutionArray[i]][0] = i;
        orderMap[initSolutionArray[i]][5] = 1;
      } else {
        availCapacity[i] = availCapacity[i - 1] + orderWeight[initSolutionArray[i]];
        orderMap[initSolutionArray[i]][4] = orderMap[initSolutionArray[i]][4] + i
            - orderMap[initSolutionArray[i]][1];
        orderMap[initSolutionArray[i]][1] = i;
      }
    }
    for (i = min; i <= max; i++) {
      orderMap[initSolutionArray[i]][5] = 0;
    }
  }

  private int calculateProfit(int s, int d, int newS, int newD) {
    int distDiff = 0, distDiffs = 0, distDiffd = 0, distDiffnewS = 0, distDiffnewD = 0;

    if (d - s != 1) {
      distDiffs = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
          initLocaton[s - 1], initLocaton[s + 1]);
      distDiffd = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[d],
          initLocaton[d - 1], initLocaton[d + 1]);
    } else {
      distDiffd = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s], initLocaton[d],
          initLocaton[s - 1], initLocaton[d + 1]);
    }

    if (newS > s) {
      if (newD - newS != 1) {
        distDiffnewS = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
            initLocaton[newS + 1], initLocaton[newS + 2]);
        distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[d],
            initLocaton[newD], initLocaton[newD + 1]);
      } else {
        distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
            initLocaton[d], initLocaton[newD], initLocaton[newD + 1]);
      }

    } else {
      if (newD - newS != 1) {
        distDiffnewS = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
            initLocaton[newS - 1], initLocaton[newS]);
        distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[d],
            initLocaton[newD - 2], initLocaton[newD - 1]);
      } else {
        distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
            initLocaton[d], initLocaton[newS - 1], initLocaton[newS]);
      }
    }

    distDiff = distDiffs + distDiffd - distDiffnewS - distDiffnewD;
    return distDiff;
  }

  @Override
  public Solution mutate(Solution parent, Random r) {
    int[][] data;
    int i, j, t;

    data = parent.copyData();

    // only the first car is used: data[0]
    i = r.nextInt(data[0].length);
    do {
      j = r.nextInt(data[0].length);
    } while (i == j);

    // swap the location of two orders
    t = data[0][i];
    data[0][i] = data[0][j];
    data[0][j] = t;

    return new Solution(data);
  }

}

// for (i = pos1 + 1; i < pos2; i++) {
// if (initSolutionArray[i] > 2 * orderNum) {
// orderMap[initSolutionArray[i]][0]--;
// continue;
// }
//
// if (i == orderMap[initSolutionArray[i]][0]) {
// orderMap[initSolutionArray[i]][0]--;
// orderMap[initSolutionArray[i]][3]--;
// } else if (i == orderMap[initSolutionArray[i]][1]) {
// orderMap[initSolutionArray[i]][1]--;
// orderMap[initSolutionArray[i]][4]--;
// } else {
// System.out.println("ERROR in the orderMap");
// }
// }
// for (i = pos2 + 1; i <= max; i++) {
// if (initSolutionArray[i] > 2 * orderNum) {
// orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] - 2;
// continue;
// }
// if (i == orderMap[initSolutionArray[i]][0]) {
// orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] - 2;
// orderMap[initSolutionArray[i]][3] = orderMap[initSolutionArray[i]][3] - 2;
// } else if (i == orderMap[initSolutionArray[i]][1]) {
// orderMap[initSolutionArray[i]][1] = orderMap[initSolutionArray[i]][1] - 2;
// orderMap[initSolutionArray[i]][4] = orderMap[initSolutionArray[i]][4] - 2;
// } else {
// System.out.println("ERROR in the orderMap");
// }
// }
//
// for (i = newpos1; i <= newpos2; i++) {
// if(newpos1 == newpos2 -1 )continue;
//
// if (initSolutionArray[i] > 2 * orderNum) {
// orderMap[initSolutionArray[i]][0]++;
// continue;
// }
// if (i == orderMap[initSolutionArray[i]][0]) {
// orderMap[initSolutionArray[i]][0]++;
// orderMap[initSolutionArray[i]][3]++;
// } else if (i == orderMap[initSolutionArray[i]][1]) {
// orderMap[initSolutionArray[i]][1]++;
// orderMap[initSolutionArray[i]][4]++;
// } else {
// System.out.println("ERROR in the orderMap");
// }
// }
// for (i = newpos2; i <= max; i++) {
// if (initSolutionArray[i] > 2 * orderNum) {
// orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + 2;
// continue;
// }
// if (i == orderMap[initSolutionArray[i]][0]) {
// orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + 2;
// orderMap[initSolutionArray[i]][3] = orderMap[initSolutionArray[i]][3] + 2;
// } else if (i == orderMap[initSolutionArray[i]][1]) {
// orderMap[initSolutionArray[i]][1] = orderMap[initSolutionArray[i]][1] + 2;
// orderMap[initSolutionArray[i]][4] = orderMap[initSolutionArray[i]][4] + 2;
// } else {
// System.out.println("ERROR in the orderMap");
// }
// }
//
// for (i = 0; i < 5; i++) {
// orderMap[initSolutionArray[pos1]][i] = orderMap[initSolutionArray[newpos1]][i];
// orderMap[initSolutionArray[pos2]][i] = orderMap[initSolutionArray[newpos2]][i];
// }
