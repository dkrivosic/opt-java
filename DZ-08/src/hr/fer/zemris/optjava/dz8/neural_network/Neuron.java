package hr.fer.zemris.optjava.dz8.neural_network;

/**
 * A neuron.
 */
public class Neuron {
	private double value;
	private ITransferFunction function;

	public Neuron(ITransferFunction function) {
		this.function = function;
		this.value = 0;
	}

	/**
	 * Returns output from a neuron.
	 */
	public double getOutput() {
		return function.valueAt(value);
	}

	/**
	 * Adds value to neuron's input.
	 */
	public void addValue(double value) {
		this.value += value;
	}

	/**
	 * Resets neuron's input to 0.
	 */
	public void reset() {
		this.value = 0.0;
	}

	/**
	 * Sets the value to given value. Used for initializing input layer.
	 */
	public void setValue(double value) {
		this.value = value;
	}

}
