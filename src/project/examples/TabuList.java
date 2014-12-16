package project.examples;

//based on this paper:  Solving the pickup and delivery problem with time windows using reactive tabu search


public class TabuList {

  int[][] tabuList;
  int tabuLength = 0;

  @SuppressWarnings("unqualified-field-access")
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

  @SuppressWarnings("unqualified-field-access")
  public void tabuMove(int pos1, int pos2) { // tabus the swap operation
    tabuList[pos1][pos2] = tabuLength;
    tabuList[pos2][pos1] = tabuLength;

  }

  @SuppressWarnings("unqualified-field-access")
  public void decrementTabu() {
    for (int i = 0; i < tabuList.length; i++) {
      for (int j = 0; j < tabuList.length; j++) {
        tabuList[i][j] -= tabuList[i][j] <= 0 ? 0 : 1;
      }
    }
  }

}
