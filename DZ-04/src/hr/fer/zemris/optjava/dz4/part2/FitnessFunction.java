package hr.fer.zemris.optjava.dz4.part2;

public class FitnessFunction {
	public final double k = 2;

	/**
	 * Evaluates chromosome and return its fitness.
	 */
	public double evaluate(Chromosome c) {
		int n = c.places.length;
		double sum = 0;
		for (int i = 0; i < n; i++) {
			sum += Math.pow((double) c.places[i].size / c.places[i].capacity, k);
		}
		return sum / n;
	}
}
