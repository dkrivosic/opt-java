package hr.fer.zemris.optjava.dz9.mutation;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public interface IMutation {

	/**
	 * Mutates given chromosome.
	 */
	public Chromosome mutate(Chromosome c);
}
