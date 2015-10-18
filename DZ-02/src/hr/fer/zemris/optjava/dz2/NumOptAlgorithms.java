package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class NumOptAlgorithms {
	private final static double e = 1e-3;

	public static Matrix gradientDescent(IFunction function, int maxIterations, Matrix x) {
		int k = 0;

		do {
			k++;
//			x.print(x.getRowDimension(), x.getColumnDimension());
			if (function.getGradientValue(x).normF() < e)
				break;

			Matrix d = function.getGradientValue(x).times(-1);
			double lambda = bisection(function, x, d);
			x = x.plus(d.times(lambda));

		} while (k < maxIterations);

		return x;
	}

	public static Matrix newtonsMethod(IHFunction function, int maxIterations, Matrix x) {
		int k = 0;
		do {
			k++;
//			x.print(x.getRowDimension(), x.getColumnDimension());
			if (function.getGradientValue(x).normF() < e)
				break;

			Matrix d = function.getHessianMatrix(x).inverse().times(-1).times(function.getGradientValue(x));
			double lambda = bisection(function, x, d);
			x = x.plus(d.times(lambda));

		} while (k < maxIterations);

		return x;
	}

	/**
	 * Bisection method for calculating lambda.
	 */
	private static double bisection(IFunction function, Matrix x, Matrix d) {
		final int maxIterations = 1000;
		double lower = 0;
		double upper = 0.01;
		double dTheta;
		
		do {
			dTheta = function.getGradientValue(x.plus(d.times(upper))).transpose().times(d).get(0, 0);

			if (dTheta > 0) {
				break;
			} else {
				upper *= 2;
			}
			
		} while (true);

		double lambda;
		int k = 0;
		do {
			k++;
			lambda = (lower + upper) / 2;
			dTheta = function.getGradientValue(x.plus(d.times(lambda))).transpose().times(d).get(0, 0);
			if (dTheta > 0) {
				upper = lambda;
			} else if (dTheta < 0) {
				lower = lambda;
			}
			
		} while (k < maxIterations && Math.abs(dTheta) > e);

		return lambda;
	}
	
}
