package hr.fer.zemris.optjava.ga.generic;

/**
 * Mutation operator.
 */
public interface IMutation<T> {

	/**
	 * Takes a solution and performs mutation.
	 */
	public void mutate(GASolution<T> solution);
}
