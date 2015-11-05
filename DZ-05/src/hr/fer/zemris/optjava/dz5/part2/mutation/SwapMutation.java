package hr.fer.zemris.optjava.dz5.part2.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

/**
 * Mutates a chromosome by randomly choosing two indexes and swapping elements
 * on those indexes.
 */
public class SwapMutation implements IMutation {
	private Random random;

	public SwapMutation() {
		this.random = new Random();
	}

	@Override
	public void mutate(Chromosome c) {
		int n = c.p.length;
		int firstPoint = random.nextInt(n);
		int secondPoint = random.nextInt(n);

		int tmp = c.p[firstPoint];
		c.p[firstPoint] = c.p[secondPoint];
		c.p[secondPoint] = tmp;
	}

}
