package hr.fer.zemris.optjava.dz5.part2.selection;

import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

/**
 * Selection operator.
 */
public interface ISelection {

	/**
	 * Selects one chromosome from population.
	 */
	public Chromosome select(Set<Chromosome> population);
}
