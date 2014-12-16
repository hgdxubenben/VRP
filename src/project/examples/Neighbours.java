package project.examples;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unused")
public class Neighbours {

  /**
   * @param args
   */

  private int[][] initSolution, newSolution, availableSpace;

  private Map<Integer,int[]> order = new HashMap<Integer,int[]>();
  
  public Neighbours(int[][] init) {
    this.initSolution = init;
    int cars = ExampleNullaryOperator.scenario.carCount();
    int len1=init.length, len2=0, carSpace=0;
    int[] temp ;
    Set<Integer> orderInCar = new HashSet<>();
    for(int i=0;i<len1;++i){
      len2 = init[i].length;
      carSpace = ExampleNullaryOperator.scenario.carCapacity(i);
      orderInCar.clear();
       for(int j=0;j<len2;++j){
         Integer index = new Integer(init[i][j]);
         if(orderInCar.contains(index)){
           carSpace += ExampleNullaryOperator.scenario.orderWeight(init[i][j]);
           this.availableSpace[i][j]= carSpace;
         }
         else{
           carSpace -= ExampleNullaryOperator.scenario.orderWeight(init[i][j]);
           this.availableSpace[i][j]= carSpace;
         }
         
         if(this.order.containsKey(index)){
           temp = this.order.get(index);
           temp[1]=j;
           this.order.put(index, temp);
         }
         else{
           temp = new int[2];
           temp[0] = j;
           this.order.put(index, temp);
         }
       }
    }
  
  }
  
  
  
  

}
/*
public class Neighbours {

  /**
   * @param args
   */
//  @SuppressWarnings("unused")
//  public ArrayList<ArrayList<Integer>> carList = new ArrayList<ArrayList<Integer>>();
//
//  public ArrayList<Map<Integer, Integer>> carOrderMaps = new ArrayList<Map<Integer, Integer>>();
//  public int cars = ExampleNullaryOperator.scenario.carCount();
//
//  public Neighbours() {
//    // TODO Auto-generated constructor stub
//  }
//
//  public Neighbours(Integer[][] initSolution) {
//
//    int initSolutionLength = initSolution.length;
//
//    for (int i = 0; i < this.cars; i++) {
//      if (i < initSolutionLength) {
//        ArrayList<Integer> orderList = (ArrayList<Integer>) Arrays.asList(initSolution[i]);
//        this.carList.add(orderList);
//
//        Map<Integer, Integer> orderMap = new HashMap<>();
//        int ordersInCar = initSolution[i].length;
//        for (int j = 0; j < ordersInCar; ++j) {
//          if (orderMap.containsKey(initSolution[i][j])) {
//            orderMap.put(initSolution[i][j], new Integer(j));
//          } else {
//            orderMap.put(initSolution[i][j], new Integer(-1));
//          }
//        }
//        this.carOrderMaps.add(orderMap);
//      } else {
//        Map<Integer, Integer> orderMap = new HashMap<>();
//        this.carOrderMaps.add(orderMap);
//      }
//    }
//    // TODO Auto-generated constructor stub
//  }
//
//  @SuppressWarnings({
//      "rawtypes", "boxing" })
//  public Boolean moveSPI() {
//    Map<Integer, Integer> orderMap;
//    Iterator iter;
//    int i, j;
//    for (i = 0; i < this.cars; i++) {
//      orderMap = this.carOrderMaps.get(i);
//      iter = orderMap.entrySet().iterator();
//      while (iter.hasNext()) {
//        Map.Entry entry = (Map.Entry) iter.next();
//        Object key = entry.getKey();
//        Object val = entry.getValue();
//        for (j = 0; j < this.cars; j++) {
//          if (j == i) continue;
//          
//        }
//      }
//
//    }
//    return false;
//  }
//
//  @SuppressWarnings("boxing")
//  public Boolean moveSBR() {
//
//    return false;
//  }
//
//  @SuppressWarnings("boxing")
//  public Boolean moveWRI() {
//
//    return false;
//  }
//
//}
