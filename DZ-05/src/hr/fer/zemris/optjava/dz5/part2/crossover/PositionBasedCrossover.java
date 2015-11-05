package hr.fer.zemris.optjava.dz5.part2.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;
import hr.fer.zemris.optjava.dz5.part2.CostFunction;

/**
 * Performs position based crossover (POS) and returns child with lower cost.
 */
public class PositionBasedCrossover implements ICrossoverOperation {
	private Random random;
	private CostFunction function;

	public PositionBasedCrossover(Random random, CostFunction function) {
		this.random = random;
		this.function = function;
	}

	@Override
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = function.getN();

		int nPoints = n / 2;
		List<Integer> points = new ArrayList<>();
		for (int i = 0; i < nPoints; i++) {
			int point = random.nextInt(n);
			if (!points.contains(point)) {
				points.add(point);
			}
		}

		Chromosome firstChild = new Chromosome(n);
		Chromosome secondChild = new Chromosome(n);

		for (int point : points) {
			firstChild.p[point] = secondParent.p[point];
			secondChild.p[point] = firstParent.p[point];
		}

		fillChild(firstParent, n, firstChild);
		fillChild(secondParent, n, secondChild);
		firstChild.evaluate(function);
		secondChild.evaluate(function);

		if (firstChild.getCost() < secondChild.getCost()) {
			return firstChild;
		} else {
			return secondChild;
		}
	}

	private void fillChild(Chromosome parent, int n, Chromosome child) {
		int i = 0;
		int j = 0;
		while (i < n && j < n) {
			if (contains(child.p, parent.p[i])) {
				i++;
			} else if (child.p[j] != -1) {
				j++;
			} else {
				child.p[j] = parent.p[i];
			}
		}
	}

	private boolean contains(int[] a, int b) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == b) {
				return true;
			}
		}
		return false;
	}

}
