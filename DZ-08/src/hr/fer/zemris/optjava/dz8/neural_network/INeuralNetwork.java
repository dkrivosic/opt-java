package hr.fer.zemris.optjava.dz8.neural_network;

/**
 * Artificial neural network.
 */
public interface INeuralNetwork {

	/**
	 * @return Total number of weights in neural network.
	 */
	public int getWeightsCount();

	/**
	 * Calculate outputs given inputs and weights. Outputs are writren in
	 * <code>outputs</code> array.
	 */
	public void calcOutputs(double[] inputs, double[] weights, double outputs[]);

	/**
	 * Calculates error for given weights.
	 */
	public double getError(double[] weights);
}
