package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IHFunction extends IFunction {

	/**
	 * @return Hessian matrix at given point <code>x</code>.
	 */
	public Matrix getHessianMatrix(Matrix x);
}
