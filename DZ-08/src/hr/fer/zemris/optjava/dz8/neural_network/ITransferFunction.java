package hr.fer.zemris.optjava.dz8.neural_network;

/**
 * Neuron transfer function.
 */
public interface ITransferFunction {

	/**
	 * @return Transfer function value at point <code>x</code>.
	 */
	public double valueAt(double x);
	
}
