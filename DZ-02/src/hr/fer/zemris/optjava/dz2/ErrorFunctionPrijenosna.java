package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class ErrorFunctionPrijenosna implements IHFunction {
	private Matrix x;
	private Matrix y;
	private int m; // Number of training examples

	public ErrorFunctionPrijenosna(Matrix x, Matrix y, int m) {
		this.x = x;
		this.y = y;
		this.m = m;
	}

	@Override
	public int getNumberOfVariables() {
		return 6; // Variables are a, b, c, d, e, f
	}

	@Override
	public double getFunctionValue(Matrix coef) {
		double err = 0;
		double a = coef.get(0, 0);
		double d = coef.get(3, 0);
		double b = coef.get(1, 0);
		double e = coef.get(4, 0);
		double c = coef.get(2, 0);
		double f = coef.get(5, 0);

		for (int i = 0; i < m; i++) {
			double x1 = x.get(i, 0);
			double x2 = x.get(i, 1);
			double x3 = x.get(i, 2);
			double x4 = x.get(i, 3);
			double x5 = x.get(i, 4);

			err += Math.pow(a * x1 + b * Math.pow(x1, 3) * x2 + c * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))
					+ f * x4 * Math.pow(x5, 2) - y.get(i, 0), 2);
		}

		return err / m;
	}

	@Override
	public Matrix getGradientValue(Matrix coef) {
		int n = coef.getRowDimension();
		Matrix grad = new Matrix(n, 1);

		double a = coef.get(0, 0);
		double b = coef.get(1, 0);
		double c = coef.get(2, 0);
		double d = coef.get(3, 0);
		double e = coef.get(4, 0);
		double f = coef.get(5, 0);

		for (int k = 0; k < n; k++) {
			double tmp = 0.0;
			for (int i = 0; i < m; i++) {
				double x1 = x.get(i, 0);
				double x2 = x.get(i, 1);
				double x3 = x.get(i, 2);
				double x4 = x.get(i, 3);
				double x5 = x.get(i, 4);

				double df = 2
						* (a * x1 + b * Math.pow(x1, 3) * x2 + c * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))
								+ f * x4 * Math.pow(x5, 2) - y.get(i, 0));

				switch (k) {
				case 0:
					tmp += df * x1;
					break;
				case 1:
					tmp += df * Math.pow(x1, 3) * x2;
					break;
				case 2:
					tmp += df * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4));
					break;
				case 3:
					tmp += df * c * x3 * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4));
					break;
				case 4:
					tmp += df * -1 * c * Math.pow(Math.E, d * x3) * Math.sin(e * x4) * x4;
					break;
				case 5:
					tmp += df * x4 * Math.pow(x5, 2);
					break;
				}
			}
			grad.set(k, 0, tmp / m);
		}
		return grad;
	}

	@Override
	public Matrix getHessianMatrix(Matrix coef) {
		double a = coef.get(0, 0);
		double b = coef.get(1, 0);
		double c = coef.get(2, 0);
		double d = coef.get(3, 0);
		double e = coef.get(4, 0);
		double f = coef.get(5, 0);

		Matrix hess = new Matrix(6, 6);

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				hess.set(i, j, 0);

		for (int i = 0; i < m; i++) {
			double x1 = x.get(i, 0);
			double x2 = x.get(i, 1);
			double x3 = x.get(i, 2);
			double x4 = x.get(i, 3);
			double x5 = x.get(i, 4);

			double dV = a * x1 + b * Math.pow(x1, 3) * x2 + c * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))
					+ f * x4 * Math.pow(x5, 2) - y.get(i, 0);

			double da = x1;
			double db = x2 * Math.pow(x1, 3);
			double dc = Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4));
			double dd = c * x3 * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4));
			double de = c * Math.pow(Math.E, d * x3) * (-Math.sin(e * x4)) * x4;
			double df = x4 * Math.pow(x5, 2);

			hess.set(0, 0, hess.get(0, 0) + 2 * da * da);
			hess.set(0, 1, hess.get(0, 1) + 2 * da * db);
			hess.set(0, 2, hess.get(0, 2) + 2 * da * dc);
			hess.set(0, 3, hess.get(0, 3) + 2 * da * dd);
			hess.set(0, 4, hess.get(0, 4) + 2 * da * de);
			hess.set(0, 5, hess.get(0, 5) + 2 * da * df);

			hess.set(1, 0, hess.get(1, 0) + 2 * db * da);
			hess.set(1, 1, hess.get(1, 1) + 2 * db * db);
			hess.set(1, 2, hess.get(1, 2) + 2 * db * dc);
			hess.set(1, 3, hess.get(1, 3) + 2 * db * dd);
			hess.set(1, 4, hess.get(1, 4) + 2 * db * de);
			hess.set(1, 5, hess.get(1, 5) + 2 * db * df);

			hess.set(2, 0, hess.get(2, 0) + 2 * dc * da);
			hess.set(2, 1, hess.get(2, 1) + 2 * dc * db);
			hess.set(2, 2, hess.get(2, 2) + 2 * dc * dc);
			hess.set(2, 3,
					hess.get(2, 3) + 2 * (dd * dc + dV * x3 * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))));
			hess.set(2, 4, hess.get(2, 4) + 2 * (dc * de + dV * Math.pow(Math.E, d * x3) * x4 * (-Math.sin(e * x4))));
			hess.set(2, 5, hess.get(2, 5) + 2 * dc * df);

			hess.set(3, 0, hess.get(3, 0) + 2 * dd * da);
			hess.set(3, 1, hess.get(3, 1) + 2 * dd * db);
			hess.set(3, 2,
					hess.get(3, 2) + 2 * (dd * dc + dV * x3 * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))));
			hess.set(3, 3, hess.get(3, 3) + 2 * (dd * dd + dV * dd * x3));
			hess.set(3, 4, hess.get(3, 4)
					+ 2 * (dd * de + dV * c * Math.pow(Math.E, d * x3) * (-Math.cos(e * x4) * Math.pow(x4, 2))));
			hess.set(3, 5, hess.get(3, 5) + 2 * dd * df);

			hess.set(4, 0, hess.get(4, 0) + 2 * de * da);
			hess.set(4, 1, hess.get(4, 1) + 2 * de * db);
			hess.set(4, 2, hess.get(4, 2) + 2 * (de * dc + dV * Math.pow(Math.E, d * x3) * (-Math.sin(e * x4)) * x4));
			hess.set(4, 3,
					hess.get(4, 3) + 2 * (de * dd + dV * c * x3 * x4 * Math.pow(Math.E, d * x3) * (-Math.sin(e * x4))));
			hess.set(4, 4, hess.get(4, 4)
					+ 2 * (de * de + dV * c * Math.pow(Math.E, d * x3) * (-Math.cos(e * x4)) * Math.pow(x4, 2)));
			hess.set(4, 5, hess.get(4, 5) + 2 * de * df);

			hess.set(5, 0, hess.get(5, 0) + 2 * df * da);
			hess.set(5, 1, hess.get(5, 1) + 2 * df * db);
			hess.set(5, 2, hess.get(5, 2) + 2 * df * dc);
			hess.set(5, 3, hess.get(5, 3) + 2 * df * dd);
			hess.set(5, 4, hess.get(5, 4) + 2 * df * de);
			hess.set(5, 5, hess.get(5, 5) + 2 * df * df);

		}

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				hess.set(i, j, hess.get(i, j) / m);

		return hess;
	}

}
