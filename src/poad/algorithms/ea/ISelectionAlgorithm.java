package poad.algorithms.ea;

import java.util.Random;

import poad.Individual;

/** the interface for selection algorithms */
public interface ISelectionAlgorithm {
  /**
   * Fill the mating pool with selected individuals from the population
   * 
   * @param pop
   *          the population of the current individuals
   * @param mate
   *          the mating pool to be filled with individuals
   * @param r
   *          the random number generator
   */
  public abstract void select(final Individual<?, ?>[] pop, final Individual<?, ?>[] mate,
      final Random r);
}
