package hr.fer.zemris.optjava.ga.operators.abs;

import hr.fer.zemris.optjava.ga.struct.Chromosome;

/**
 * Mutation operator.
 */
public interface IMutation {

	/**
	 * Mutates chromosome <code>c</code>.
	 * 
	 * @param c
	 *            chromosome
	 */
	public void mutate(Chromosome c);
}
