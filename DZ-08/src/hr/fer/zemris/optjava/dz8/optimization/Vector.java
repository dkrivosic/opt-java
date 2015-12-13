package hr.fer.zemris.optjava.dz8.optimization;

public class Vector {
	public double[] values;
	private double error;

	public Vector(double[] values) {
		this.values = values;
	}

	public Vector(int n) {
		this.values = new double[n];
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	/**
	 * Adds two vectors and returns result vector.
	 */
	public Vector add(Vector v) {
		int d = v.values.length;
		double[] val = new double[d];
		for (int i = 0; i < d; i++) {
			val[i] = this.values[i] + v.values[i];
		}
		return new Vector(val);
	}

	/**
	 * Multiplies vector with scalar and returns result vector.
	 */
	public Vector mul(double x) {
		int d = this.values.length;
		double[] val = new double[d];
		for (int i = 0; i < d; i++) {
			val[i] = this.values[i] * x;
		}
		return new Vector(val);
	}

	/**
	 * Subtracts given vector from this vector and returns result vector.
	 */
	public Vector sub(Vector v) {
		int d = v.values.length;
		double[] val = new double[d];
		for (int i = 0; i < d; i++) {
			val[i] = this.values[i] - v.values[i];
		}
		return new Vector(val);
	}
	
	@Override
	public String toString() {
		String str = "[ ";
		for(double x : values) {
			str += x + " ";
		}
		str += "]";
		return str;
	}

}
