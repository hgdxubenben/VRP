package project.examples;

import java.util.Random;
import poad.INullarySearchOperation;
import project.framework.Scenario;
import project.framework.Solution;

/**
 * This operator creates a single, random candidate solution.
 */
public class ExampleNullaryOperator implements INullarySearchOperation<Solution> {

  /** the scenario */
  static Scenario scenario;

  int[] startState, availCapacityNow, locationState;
  int[][] orderMap;
  Solution startSolution;
  /** Create */
  public ExampleNullaryOperator() {
    super();
    prepareData();
  }

  /**
   * Create a random solution. Basic idea: create a permutation of all the orders (the int array).
   * The array holds each order ID two times, the first time for pick-up, the second time for
   * delivery. Here, we just keep these two occurrences together, i.e., get permutations like
   * "1 1 5 5 3 3 2 2 4 4". This way, we ensure that the vehicle's capacity is never exceeded.
   * 
   * @param r
   *          the randomizer
   * @return the solution
   */
  @Override
  public Solution create(Random r) {
    final int oc;
    final int[] solution;
    int j, i, k, a, b;

    oc = scenario.orderCount();
    j = (oc << 1);
    solution = new int[j];
    for (i = oc; (--i) >= 0;) {
      solution[--j] = i;
      solution[--j] = i;
    }

    for (i = oc; (--i) > 0;) {
      j = r.nextInt(i);

      a = (i << 1);
      b = (j << 1);
      k = solution[a];
      solution[a] = solution[b];
      solution[b] = k;

      a++;
      b++;
      k = solution[a];
      solution[a] = solution[b];
      solution[b] = k;
    }

    return new Solution(solution);
  }
  
  private void prepareData(){
    int carNum = scenario.carCount(), orderNum = scenario.orderCount();
    int N = carNum + 2 * orderNum + 1;
    int[] start = new int[N];
    int[] availCapacity = new int[N];
    int [][] order = new int[N][5];
    int[] location = new int[N];
    
    int[] carCapacity = ExampleNullaryOperator.scenario.getAllCarCapacity();
    int[] orderWeight = ExampleNullaryOperator.scenario.getAllOrderWeight();
    Solution startSol = create(new Random());
    int[][] startArray = startSol.copyData();
    int i = 0, j = 0, index = 0, lenCow = startArray.length, lenCol = 0, capcity =0 ;
    for (; i < carNum; ++i) {
      capcity =  carCapacity[i];
      start[index] = orderNum + i + 1;
      availCapacity[index] = capcity;
      order[orderNum + i + 1][0] = index;
      location[index] = 0;
      index++;
      if (i >= lenCow) continue;
      lenCol = startArray[i].length;
      int orderN = 0;
      for (; j < lenCol; ++j) {
        orderN = startArray[i][j];
        start[index] = orderN;
        
        //pick up
        if(order[orderN][0]==0){
          order[orderN][0] = index;
          order[orderN][2] = i;
          order[orderN][3] = j;
          capcity -= orderWeight[orderN];
          availCapacity[index] = capcity;
          location[index] = ExampleNullaryOperator.scenario.orderPickupLocation(orderN);
        }
        else{//download
          order[orderN][1] = index;
          order[orderN][4] = j;
          capcity += orderWeight[orderN];
          availCapacity[index] = capcity;
          location[index] = ExampleNullaryOperator.scenario.orderDeliveryLocation(orderN);
        }
        
        index++;
      }

    }
    this.startState = start;
    this.availCapacityNow = availCapacity;
    this.orderMap = order;
    this.locationState = location;
    this.startSolution = startSol;
    
  }

  public int[] getStartSolutionArray() {
    return this.startState;
  }
  public int[] getStartAvailCapacity(){
    return this.availCapacityNow;
  }
  public int[][] getStartOrderMap(){
    return this.orderMap;
  }
  public int[] getStartLocation(){
    return this.locationState;
  }
  public Solution getStartSolution() {
    return this.startSolution;
  }
}
