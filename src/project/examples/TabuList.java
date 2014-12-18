package project.examples;

//based on this paper:  Solving the pickup and delivery problem with time windows using reactive tabu search

@SuppressWarnings("unqualified-field-access")
public class TabuList {

  int[][] tabuList;
  int tabuLength = 0;

  public TabuList() {
    int orderNumber = ExampleNullaryOperator.scenario.orderCount();
    int carNumber = ExampleNullaryOperator.scenario.carCount();
    int N =  2*orderNumber + carNumber +1;
    // the row index identifies the customer, and the column index depicts the solution position
    tabuList = new int[N][N]; 
    if (tabuLength > 30) {
      tabuLength = 30;
    }
  } 


  public void tabuMove(int order, int newS, int newD, int iterator) { // tabus the swap operation
    tabuList[order][newS] = tabuLength + iterator;
    tabuList[order][newD] = tabuLength + iterator;
  }
  public void tabuMove2(int order, int newS, int iterator) { // tabus the swap operation
    tabuList[order][newS] = tabuLength + iterator;
  }
  
//  public void decrementTabu() {
//    for (int i = 0; i < tabuList.length; i++) {
//      for (int j = 0; j < tabuList.length; j++) {
//        tabuList[i][j] -= tabuList[i][j] <= 0 ? 0 : 1;
//      }
//    }
//  }
  
  @SuppressWarnings("boxing")
  public Boolean shouldTabu(int order, int newS, int newD, int iterator){
    if(this.tabuList[order][newS]<=iterator || this.tabuList[order][newD]<=iterator){
      return false;
    }
    
    return true;
  }

}
