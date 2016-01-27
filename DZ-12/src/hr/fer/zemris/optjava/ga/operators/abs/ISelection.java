package hr.fer.zemris.optjava.ga.operators.abs;

import java.util.List;

import hr.fer.zemris.optjava.ga.struct.Chromosome;

/**
 * Selection operator.
 */
public interface ISelection {

	/**
	 * Selects one chromosome from the population.
	 * 
	 * @param population Population
	 * @return Chromosome
	 */
	public Chromosome select(List<Chromosome> population);

}
