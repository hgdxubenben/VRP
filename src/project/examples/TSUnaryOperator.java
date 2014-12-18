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
    System.out.println(Arrays.toString(initSolutionArray));
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
    int i, j, k, p, s, d;
    int curBenefit = 0, bestBenefit = Integer.MIN_VALUE;
    Boolean flag1 = true;
    Boolean flag2 = true;

    // SPI && WRI
    // one bug here: WRI ,the load available check should be changed
    for (i = 0; i < orderNum; ++i) {
      s = orderMap[i][0];
      d = orderMap[i][1];
      for (j = 1; j < N; ++j) {
        if (j == s) j = d + 1;
        for (k = j; k < N; ++k) {
          if (j < s && k == s - 1) break;

          if (initSolutionArray[k] < orderNum) {
            // check whether the car can load the new insert order
            if (availCapacity[k - 1] < orderWeight[i]) {
              if (k >= orderMap[i][0] && k < orderMap[i][1]) {
                flag2 = true;
              } else flag2 = false;
            } else {
              if (tabuList.shouldTabu(i, j, orderMap[i][0], iterator)
                  && tabuList.shouldTabu(i + orderNum, k, orderMap[i][1], iterator)) continue;
              curBenefit = calculateProfit(s, d, j, k);
              if (curBenefit > bestBenefit) {

                bestBenefit = curBenefit;
                pos1 = s;
                pos2 = d;
                newpos1 = j;
                newpos2 = k;
                if (newpos1 == newpos2) {
                  if (newpos1 > pos1) newpos1--;
                  if (newpos1 < pos1) newpos2++;
                }
                orderMovS = i;
              }
            }
          } else {
            flag1 = false;
            break;
          }
          if (flag2 == false) break;
        }

      }
    }

    update();

    int[][] returnResult = new int[carNum][];
    for (i = 0; i < carNum; ++i) {
      int carStart = orderMap[orderNum + i + 1][0];
      int nextCarStart = orderMap[orderNum + i + 1 + 1][0];
      int len = nextCarStart - carStart - 1;
      if (len == 0) continue;
      int[] result = new int[len];
      for (j = 0; j < len; ++j) {
        result[j] = initSolutionArray[carStart + 1 + j];
      }
      returnResult[i] = result;
    }
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
    for (i = 0; i < 5; i++) {
      orderMap[initSolutionArray[pos1]][i] = orderMap[initSolutionArray[newpos1]][i];
      orderMap[initSolutionArray[pos2]][i] = orderMap[initSolutionArray[newpos2]][i];
    }
    if (pos1 < newpos1) {
      for (i = min + 1; i <= max; ++i) {
        if (i < pos2) dif = -1;
        else if (i == pos2) dif = 0;
        else if (i < newpos1 + 2) dif = -2;
        else dif = -1;

        if (initSolutionArray[i] > orderNum) {
          orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + dif;
          continue;
        }
        if (i == orderMap[initSolutionArray[i]][0]) {
          orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + dif;
          orderMap[initSolutionArray[i]][3] = orderMap[initSolutionArray[i]][3] + dif;
        } else if (i == orderMap[initSolutionArray[i]][1]) {
          orderMap[initSolutionArray[i]][1] = orderMap[initSolutionArray[i]][1] + dif;
          orderMap[initSolutionArray[i]][4] = orderMap[initSolutionArray[i]][4] + dif;
        } else {
          System.out.println("ERROR in the orderMap");
        }
      }
    } else {

      for (i = min; i < max; ++i) {
        if (i <= newpos2 - 2) dif = 1;
        else if (i < pos1) dif = 2;
        else if (i == pos1) dif = 0;
        else dif = 1;

        if (initSolutionArray[i] > orderNum) {
          orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + dif;
          continue;
        }
        if (i == orderMap[initSolutionArray[i]][0]) {
          orderMap[initSolutionArray[i]][0] = orderMap[initSolutionArray[i]][0] + dif;
          orderMap[initSolutionArray[i]][3] = orderMap[initSolutionArray[i]][3] + dif;
        } else if (i == orderMap[initSolutionArray[i]][1]) {
          orderMap[initSolutionArray[i]][1] = orderMap[initSolutionArray[i]][1] + dif;
          orderMap[initSolutionArray[i]][4] = orderMap[initSolutionArray[i]][4] + dif;
        } else {
          System.out.println("ERROR in the orderMap");
        }
      }
    }
    

    i = min;
    while (i < pos1) {
      tempAvailCapciy[j] = availCapacity[i];
      tempLocation[j] = initLocaton[i];
      tempSolution[j++] = initSolutionArray[i];
      i++;
    }
    tempAvailCapciy[j] = availCapacity[i] + orderWeight[initSolutionArray[pos1]];
    i++;
    while (i < pos2) {
      tempAvailCapciy[j + 1] = availCapacity[i] + orderWeight[initSolutionArray[pos1]];
      tempLocation[j] = initLocaton[i];
      tempSolution[j++] = initSolutionArray[i];
      i++;
    }
    tempAvailCapciy[j + 1] = availCapacity[i] + orderWeight[initSolutionArray[pos1]];
    i++;
    while (i <= max) {
      tempAvailCapciy[j + 2] = availCapacity[i];
      tempLocation[j] = initLocaton[i];
      tempSolution[j++] = initSolutionArray[i];
      i++;
    }

    i = max;
    while (i > newpos2) {
      tempLocation[i] = initLocaton[i - 2];
      tempSolution[i] = tempSolution[i - 2];
      i--;
    }
    tempAvailCapciy[i] = tempAvailCapciy[i] - orderWeight[initSolutionArray[pos1]];
    tempLocation[i] = initLocaton[pos2];
    tempSolution[i--] = initSolutionArray[pos2];
    while (i > newpos1) {
      tempAvailCapciy[i] = tempAvailCapciy[i] - orderWeight[initSolutionArray[pos1]];
      tempLocation[i] = initLocaton[i - 1];
      tempSolution[i] = tempSolution[i - 1];
      i--;
    }
    tempAvailCapciy[i] = tempAvailCapciy[i] - orderWeight[initSolutionArray[pos1]];
    tempLocation[i] = initLocaton[pos1];
    tempSolution[i] = initSolutionArray[pos1];

    for (i = min; i <= max; ++i) {
      availCapacity[i] = tempAvailCapciy[i];
      initLocaton[i] = tempLocation[i];
      initSolutionArray[i] = tempSolution[i];
    }

    // // updata the initSolution TODO:bug here
    // initSolution.moveData(orderMap[initLocaton[pos1]][2], orderMap[initLocaton[pos1]][3],
    // orderMap[initLocaton[newpos1]][2], orderMap[initLocaton[newpos1]][3]);
    // initSolution.moveData(orderMap[initLocaton[pos2]][2], orderMap[initLocaton[pos2]][4],
    // orderMap[initLocaton[newpos2]][2], orderMap[initLocaton[newpos2]][4]);
    //
    // // update the orderMap TODO:Bug here
    // int i = 0;
    // for (i = 2; i < 5; ++i) {
    // int temp = orderMap[initLocaton[pos1]][i];
    // orderMap[initLocaton[pos1]][i] = orderMap[initLocaton[newpos1]][i];
    // orderMap[initLocaton[newpos1]][i] = temp;
    // }
    // for (i = 2; i < 5; ++i) {
    // int temp = orderMap[initLocaton[pos2]][i];
    // orderMap[initLocaton[pos2]][i] = orderMap[initLocaton[newpos2]][i];
    // orderMap[initLocaton[newpos2]][i] = temp;
    // }
    //
    // int weightGap = orderWeight[initLocaton[pos1]] - orderWeight[initLocaton[newpos1]];
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

    if (newD - newS != 1) {
      distDiffnewS = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s],
          initLocaton[newS - 1], initLocaton[newS + 1]);
      distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[d],
          initLocaton[newD - 1], initLocaton[newD + 1]);
    } else {
      distDiffnewD = ExampleNullaryOperator.scenario.travelCostDiff(initLocaton[s], initLocaton[d],
          initLocaton[newS - 1], initLocaton[newD + 1]);
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
