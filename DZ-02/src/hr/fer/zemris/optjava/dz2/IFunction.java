package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IFunction {

	/**
	 * @return Number of variables that define function.
	 */
	public int getNumberOfVariables();

	/**
	 * @return Function value at given point <code>x</code>.
	 */
	public double getFunctionValue(Matrix x);

	/**
	 * @return Gradient value at given point <code>x</code>.
	 */
	public Matrix getGradientValue(Matrix x);

}
