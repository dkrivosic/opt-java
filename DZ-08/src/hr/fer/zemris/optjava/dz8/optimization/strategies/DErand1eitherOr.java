package hr.fer.zemris.optjava.dz8.optimization.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz8.optimization.Vector;

public class DErand1eitherOr implements IStrategy {
	private Random random;
	private double F;
	private double pf;

	public DErand1eitherOr(double F, double pf) {
		this.random = new Random();
		this.F = F;
	}

	@Override
	public Vector generateTrial(List<Vector> population, int targetIndex) {
		int popSize = population.size();
		Set<Integer> indices = new HashSet<>();
		indices.add(targetIndex);
		// Generate mutant vector
		int r0 = getNextIndex(indices, popSize);
		int r1 = getNextIndex(indices, popSize);
		int r2 = getNextIndex(indices, popSize);
		Vector base = population.get(r0);
		Vector x1 = population.get(r1);
		Vector x2 = population.get(r2);

		Vector trial = null;
		if (random.nextDouble() < pf) {
			trial = base.add(x1.sub(x2).mul(F));
		} else {
			double K = 0.5 * (F + 1);
			trial = base.add(x1.add(x2).sub(base.mul(2).mul(K)));
		}

		return trial;
	}

	/**
	 * Generates new random index which is not contained in a set of indices.
	 */
	private int getNextIndex(Set<Integer> indices, int popSize) {
		int r;
		do {
			r = random.nextInt(popSize);
		} while (indices.contains(r));
		indices.add(r);
		return r;
	}

}
