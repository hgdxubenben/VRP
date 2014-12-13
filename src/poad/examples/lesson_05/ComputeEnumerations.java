// start
package poad.examples.lesson_05;

/** an example class enumerating all combinations */ // end
public class ComputeEnumerations {// start
  /** @param args the command line arguments */ // end
  public static void main(String[] args) {
    int meanLowerOrEqualTo4 = 0; //how often did we find a mean <= 4
    int totalCombinations   = 0; //total number of tested combinations

    for (int i = 10; i > 0; i--) {              // as O = numbers from 1 to 10
      for (int j = (i - 1); j > 0; j--) {       // we can conveniently iterate
        for (int k = (j - 1); k > 0; k--) {     // over all 4-element combos
          for (int l = (k - 1); l > 0; l--) {   // with 4 such nested loops
            if (((i + j + k + l) / 4.0) <= 4) { // check for the extreme cases
              meanLowerOrEqualTo4++; }          // count the extreme case
            totalCombinations++;                // add up combos, to verify
    } } } }

    System.out.println(meanLowerOrEqualTo4 + " " + totalCombinations);
  }
}
