package hr.fer.zemris.optjava.dz7.algorithm.pso;

public class Particle {

	private double[] x;
	private double[] v;
	private double personalBestValue;
	private double[] personalBest;
	private double error;

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double[] getPersonalBest() {
		return personalBest;
	}

	public void setPersonalBest(double[] personalBest) {
		this.personalBest = personalBest;
	}

	public double getPersonalBestValue() {
		return personalBestValue;
	}

	public void setPersonalBestValue(double personalBestValue) {
		this.personalBestValue = personalBestValue;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getV() {
		return v;
	}

	public void setV(double[] v) {
		this.v = v;
	}

}
