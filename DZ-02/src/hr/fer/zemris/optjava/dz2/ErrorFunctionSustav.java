package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class ErrorFunctionSustav implements IHFunction {
	private Matrix coefficients;
	private Matrix y;
	private int n ;
	
	
	public ErrorFunctionSustav(Matrix coefficients, Matrix y, int n) {
		this.coefficients = coefficients;
		this.y = y;
		this.n = n;
	}
	
	@Override
	public int getNumberOfVariables() {
		return n;
	}

	@Override
	public double getFunctionValue(Matrix x) {
		Matrix err = coefficients.times(x).minus(y);
		for(int i = 0; i < n; i++) {
				err.set(i, 0, Math.pow(err.get(i, 0), 2));
		}
		
		double value = 0.0;
		for(int i = 0; i < n; i++) {
			value += err.get(i, 0);
		}
		return value;
	}

	@Override
	public Matrix getGradientValue(Matrix x) {
		Matrix err = coefficients.times(x).minus(y).times(2);
		Matrix grad = new Matrix(n, 1);
		for(int i = 0; i < n; i++) {
			double value = 0.0;
			for(int j = 0; j < n; j++) {
				value += err.get(j, 0) * coefficients.get(j, i);
			}
			grad.set(i, 0, value);
		}
		
		return grad;
	}

	@Override
	public Matrix getHessianMatrix(Matrix x) {
		Matrix hess = new Matrix(n, n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					hess.set(i, j, hess.get(i, j) + 2 * coefficients.get(k, i) * coefficients.get(k, j));
					
				}
			}
		}
		return hess;
	}

}
