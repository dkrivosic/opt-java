package hr.fer.zemris.optjava.dz5.part1.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * One-point crossover.
 */
public class OnePointCrossover implements ICrossoverOperation {
	private Random random;

	public OnePointCrossover(Random random) {
		this.random = random;
	}

	@Override
	public List<Chromosome> crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = firstParent.n;
		int point = random.nextInt(n - 1) + 1;

		List<Chromosome> children = new ArrayList<>();
		Chromosome firstChild = new Chromosome(n);
		Chromosome secondChild = new Chromosome(n);

		for (int i = 0; i < point; i++) {
			firstChild.v[i] = firstParent.v[i];
			secondChild.v[i] = secondParent.v[i];
		}

		for (int i = point; i < n; i++) {
			firstChild.v[i] = secondParent.v[i];
			secondChild.v[i] = firstParent.v[i];
		}

		children.add(firstChild);
		children.add(secondChild);

		return children;
	}

}
