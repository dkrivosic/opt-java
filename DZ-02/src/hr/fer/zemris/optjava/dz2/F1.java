package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * f(x1, x2) = x1^2 + (x2 - 1)^2 
 */
public class F1 implements IHFunction {

	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getFunctionValue(Matrix x) {
		double x1 = x.get(0, 0);
		double x2 = x.get(1, 0);
		return Math.pow(x1, 2) + Math.pow(x2 - 1, 2);
	}

	@Override
	public Matrix getGradientValue(Matrix x) {
		double x1 = x.get(0, 0);
		double x2 = x.get(1, 0);
		return new Matrix(new double[][]{{2 * x1}, {2 * (x2 - 1)}});
	}

	@Override
	public Matrix getHessianMatrix(Matrix x) {
		return new Matrix(new double[][]{{2, 0},{0, 2}});
	}

}
