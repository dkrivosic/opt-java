package hr.fer.zemris.optjava.dz4.part1;

import java.util.Random;

public class Mutation {
	private double sigma;
	private Random random;

	public Mutation(Random random, double sigma) {
		this.sigma = sigma;
		this.random = random;
	}

	public void mutate(Chromosome c) {
		int n = c.values.length;
		for (int i = 0; i < n; i++) {
			c.values[i] = random.nextGaussian() * sigma;
		}
	}

}
