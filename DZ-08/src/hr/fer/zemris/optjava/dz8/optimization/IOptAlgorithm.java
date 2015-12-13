package hr.fer.zemris.optjava.dz8.optimization;

/**
 * Optimization algorithm for optimizing neural network weights.
 */
public interface IOptAlgorithm {

	/**
	 * @return Array of weights.
	 */
	public double[] run();
}
