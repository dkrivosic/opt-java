package hr.fer.zemris.optjava.dz7.decoder;

public interface IDecoder<T> {

	/**
	 * Decodes solution to array of doubles.
	 */
	public double[] decode(T solution);

	/**
	 * Decodes solution to array of doubles.
	 */
	public void decode(T solution, double[] v);
}
