package hr.fer.zemris.optjava.gp.mutation;

import hr.fer.zemris.optjava.gp.ant.Ant;

/**
 * Mutation.
 */
public interface IMutation {

	/**
	 * Mutates given ant.
	 */
	public void mutate(Ant ant);
}
