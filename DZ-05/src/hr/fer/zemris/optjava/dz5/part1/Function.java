package hr.fer.zemris.optjava.dz5.part1;

/**
 * Fitness function.
 */
public class Function {

	public double valueAt(Chromosome c) {
		int k = 0;
		for (int i = 0; i < c.n; i++) {
			k += c.v[i];
		}

		if (k <= 0.8 * c.n) {
			return (double) k / c.n;
		} else if (k > 0.8 * c.n && k <= 0.9 * c.n) {
			return 0.8;
		} else {
			return (2.0 * k / c.n) - 1;
		}
	}
}
