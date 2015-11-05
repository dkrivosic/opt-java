package hr.fer.zemris.optjava.dz5.part1.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * Two-point crossover.
 */
public class TwoPointCrossover implements ICrossoverOperation {
	private Random random;

	public TwoPointCrossover(Random random) {
		this.random = random;
	}

	@Override
	public List<Chromosome> crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = firstParent.n;
		int mid = Math.round(n / 2);
		int firstPoint = random.nextInt(mid - 1) + 1;
		int secondPoint = random.nextInt(mid - 1) + mid;
		
		List<Chromosome> children = new ArrayList<>();
		Chromosome firstChild = new Chromosome(n);
		Chromosome secondChild = new Chromosome(n);

		for (int i = 0; i < firstPoint; i++) {
			firstChild.v[i] = firstParent.v[i];
			secondChild.v[i] = secondParent.v[i];
		}

		for (int i = firstPoint; i < secondPoint; i++) {
			firstChild.v[i] = secondParent.v[i];
			secondChild.v[i] = firstParent.v[i];
		}

		for (int i = secondPoint; i < n; i++) {
			firstChild.v[i] = firstParent.v[i];
			secondChild.v[i] = secondParent.v[i];
		}

		children.add(firstChild);
		children.add(secondChild);

		return children;
	}

}
