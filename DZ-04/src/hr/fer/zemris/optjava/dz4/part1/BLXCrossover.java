package hr.fer.zemris.optjava.dz4.part1;

import java.util.Random;

public class BLXCrossover {
	private double alpha;
	private Random random;

	public BLXCrossover(Random random, double alpha) {
		this.alpha = alpha;
		this.random = random;
	}

	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = firstParent.values.length;
		Chromosome child = new Chromosome(n);

		for (int j = 0; j < n; j++) {
			double cmin = Math.min(firstParent.values[j], secondParent.values[j]);
			double cmax = Math.max(firstParent.values[j], secondParent.values[j]);
			double i = cmax - cmin;
			double range = cmax - cmin + 2 * alpha * i;
			child.values[j] = random.nextDouble() * range + cmin - i * alpha;
		}

		return child;
	}

}
