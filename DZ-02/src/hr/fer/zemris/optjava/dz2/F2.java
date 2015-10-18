package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * f(x1, x2) = (x1 - 1)^2 + 10*(x2 - 2)^2; 
 */
public class F2 implements IHFunction {

	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getFunctionValue(Matrix x) {
		double x1 = x.get(0, 0);
		double x2 = x.get(1, 0);
		return Math.pow(x1 - 1, 2) + 10 * Math.pow(x2 - 2, 2);
	}

	@Override
	public Matrix getGradientValue(Matrix x) {
		double x1 = x.get(0, 0);
		double x2 = x.get(1, 0);
		return new Matrix(new double[][]{{2 * (x1 - 1)},{20 * (x2 - 2)}});
	}

	@Override
	public Matrix getHessianMatrix(Matrix x) {
		return new Matrix(new double[][]{{2, 0},{0, 20}});
	}

}
