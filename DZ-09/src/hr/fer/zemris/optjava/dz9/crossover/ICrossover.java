package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

/**
 * Crossover operator.
 */
public interface ICrossover {

	/**
	 * Creates child by performing crossover operation on parents and returns
	 * it.
	 */
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent);

}
