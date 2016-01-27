package hr.fer.zemris.optjava.ga.operators.impl;

import java.util.List;

import hr.fer.zemris.optjava.ga.operators.abs.ISelection;
import hr.fer.zemris.optjava.ga.struct.Chromosome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Roulette wheel selection.
 */
public class RouletteWheelSelection implements ISelection {

	@Override
	public Chromosome select(List<Chromosome> population) {
		int maxError = 0;
		int sum = 0;
		for (Chromosome c : population) {
			sum += c.error;
			if (c.error > maxError) {
				maxError = c.error;
			}
		}
		sum = maxError * population.size() - sum;

		IRNG rng = RNG.getRNG();
		double p = rng.nextDouble();
		double pSum = 0;
		for (Chromosome c : population) {
			pSum += (double) (maxError - c.error) / sum;

			if (p < pSum) {
				return c;
			}
		}

		return population.get(population.size() - 1);
	}

}
