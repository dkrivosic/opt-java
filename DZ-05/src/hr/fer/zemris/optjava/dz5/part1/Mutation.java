package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

/**
 * Mutates chromosome by flipping each bit with probability of <code>pm</code>.
 */
public class Mutation {

	private Random random;
	private double pm;

	public Mutation(Random random, double pm) {
		this.random = random;
		this.pm = pm;
	}

	public void mutate(Chromosome c) {
		for (int i = 0; i < c.n; i++) {
			double p = random.nextDouble();
			if (p < pm) {
				c.v[i] = 1 - c.v[i];
			}
		}
	}

}
