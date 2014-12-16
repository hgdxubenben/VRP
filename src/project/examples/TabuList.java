package project.examples;

public class TabuList {
  
  int [][] tabuList ;
  @SuppressWarnings("unqualified-field-access")
  public TabuList(int numCities){
      tabuList = new int[numCities][numCities]; //city 0 is not used here, but left for simplicity
  }
  
  @SuppressWarnings("unqualified-field-access")
  public void tabuMove(int city1, int city2){ //tabus the swap operation
      tabuList[city1][city2]+= 5;
      tabuList[city2][city1]+= 5;
      
  }
  
  @SuppressWarnings("unqualified-field-access")
  public void decrementTabu(){
      for(int i = 0; i<tabuList.length; i++){
         for(int j = 0; j<tabuList.length; j++){
          tabuList[i][j]-=tabuList[i][j]<=0?0:1;
       } 
      }
  }
  
}

