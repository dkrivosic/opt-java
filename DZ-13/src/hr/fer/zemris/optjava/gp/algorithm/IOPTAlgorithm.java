package hr.fer.zemris.optjava.gp.algorithm;

import hr.fer.zemris.optjava.gp.ant.Ant;

/**
 * Optimization Algorithm.
 */
public interface IOPTAlgorithm {

	/**
	 * Runs the algorithm and returns the best ant as result.
	 */
	public Ant run();
}
