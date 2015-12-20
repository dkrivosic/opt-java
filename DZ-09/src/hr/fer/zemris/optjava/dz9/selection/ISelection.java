package hr.fer.zemris.optjava.dz9.selection;

import java.util.List;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

/**
 * Selection operator.
 */
public interface ISelection {

	/**
	 * Selects one Chromosome from population.
	 */
	public Chromosome select(List<Chromosome> population);
}
