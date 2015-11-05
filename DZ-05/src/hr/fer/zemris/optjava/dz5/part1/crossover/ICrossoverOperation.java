package hr.fer.zemris.optjava.dz5.part1.crossover;

import java.util.List;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * Crossover operator.
 */
public interface ICrossoverOperation {

	/**
	 * Performs crossover operation on two parents and returns list of children.
	 */
	public List<Chromosome> crossover(Chromosome firstParent, Chromosome secondParent);
}
