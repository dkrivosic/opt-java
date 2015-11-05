package hr.fer.zemris.optjava.dz5.part1.selection;

import java.util.Set;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * Selection Operator.
 */
public interface ISelection {

	/**
	 * Selects one chromosome from population.
	 */
	public Chromosome select(Set<Chromosome> population);
}
