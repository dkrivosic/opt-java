package hr.fer.zemris.optjava.ga.operators.abs;

import hr.fer.zemris.optjava.ga.struct.Chromosome;

/**
 * Crossover operator.
 */
public interface ICrossover {

	/**
	 * Method takes two parent chromosomes and returns a child generated as
	 * result of crossover operation.
	 * 
	 * @param firstParent
	 *            First parent chromosome
	 * @param secondParent
	 *            Second parent chromosome
	 * @return Child chromosome
	 */
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent);
}
