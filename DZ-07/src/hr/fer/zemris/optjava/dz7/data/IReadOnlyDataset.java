package hr.fer.zemris.optjava.dz7.data;

public interface IReadOnlyDataset {

	/**
	 * Number of samples in the dataset.
	 */
	public int numberOfSamples();

	/**
	 * Returns inputs as two-dimensional array.
	 */
	public double[][] getInputs();

	/**
	 * Returns expectd outputs as two-dimensional arrays.
	 */
	public double[][] getOutputs();
}
