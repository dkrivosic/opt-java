package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

/**
 * Solution represented by array of <code>double</code> values.
 */
public class DoubleArraySolution extends SingleObjectiveSolution {

	public double[] values;
	public int n;

	public DoubleArraySolution(int n) {
		this.values = new double[n];
		this.n = n;
	}

	@Override
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(n);
	}

	@Override
	public DoubleArraySolution duplicate() {
		DoubleArraySolution solution = new DoubleArraySolution(n);

		for (int i = 0; i < n; i++) {
			solution.values[i] = this.values[i];
		}

		return solution;
	}

	/**
	 * Fills array <code>values</code> with random values.
	 * 
	 * @param rand
	 *            random number generator
	 * @param mins
	 *            lower bound for each value
	 * @param maxs
	 *            upper bound for each value
	 */
	public void randomize(Random rand, double[] mins, double[] maxs) {
		for (int i = 0; i < n; i++) {
			values[i] = rand.nextDouble() * (maxs[i] - mins[i]) - mins[i];
		}
	}

	/**
	 * Fills array <code>values</code> with random values.
	 * 
	 * @param rand
	 *            random number generator
	 * @param mins
	 *            lower bound
	 * @param maxs
	 *            upper bound
	 */
	public void randomize(Random rand, double min, double max) {
		for (int i = 0; i < n; i++) {
			values[i] = rand.nextDouble() * (max - min) - min;
		}
	}

	@Override
	public String toString() {
		String str = "[ ";
		for (int i = 0; i < values.length; i++) {
			str += values[i] + " ";
		}
		str += "]";
		return str;
	}
}
