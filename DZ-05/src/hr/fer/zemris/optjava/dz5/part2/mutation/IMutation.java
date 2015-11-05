package hr.fer.zemris.optjava.dz5.part2.mutation;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

/**
 * Mutation operator.
 */
public interface IMutation {

	/**
	 * Mutates chromosome.
	 */
	public void mutate(Chromosome c);
}
