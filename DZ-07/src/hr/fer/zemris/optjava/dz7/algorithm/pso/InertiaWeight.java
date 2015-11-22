package hr.fer.zemris.optjava.dz7.algorithm.pso;

public class InertiaWeight {

	private double wmin;
	private double wmax;
	private int wminIteration;

	public InertiaWeight(double wmin, double wmax, int wminIteration) {
		this.wmin = wmin;
		this.wmax = wmax;
		this.wminIteration = wminIteration;
	}

	public double getWeight(int iteration) {
		if (iteration >= wminIteration) {
			return wmin;
		}

		return (double) iteration / wminIteration * (wmin - wmax) + wmax;
	}

}
