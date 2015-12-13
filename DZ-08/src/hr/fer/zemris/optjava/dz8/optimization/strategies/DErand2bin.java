package hr.fer.zemris.optjava.dz8.optimization.strategies;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz8.optimization.Vector;

public class DErand2bin implements IStrategy {
	private Random random;
	private double F;
	private double cr;

	public DErand2bin(double F, double cr) {
		this.random = new Random();
		this.F = F;
		this.cr = cr;
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
		int r3 = getNextIndex(indices, popSize);
		int r4 = getNextIndex(indices, popSize);
		Vector base = population.get(r0);
		Vector x1 = population.get(r1);
		Vector x2 = population.get(r2);
		Vector x3 = population.get(r3);
		Vector x4 = population.get(r4);

		Vector mutant = base.add(x1.sub(x2).mul(F)).add(x3.sub(x4).mul(F));
		Vector target = population.get(targetIndex);

		// Generate trial vector
		int dimension = mutant.values.length;
		Vector trial = new Vector(dimension);
		int jrand = random.nextInt(dimension);
		for (int j = 0; j < dimension; j++) {
			if (random.nextDouble() < cr || j == jrand) {
				trial.values[j] = mutant.values[j];
			} else {
				trial.values[j] = target.values[j];
			}
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
