package hr.fer.zemris.optjava.dz5.part2.crossover;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

/**
 * Crossover operation.
 */
public interface ICrossoverOperation {

	/**
	 * Performs crossover operation on two parents and returns one child.
	 */
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent);
}
