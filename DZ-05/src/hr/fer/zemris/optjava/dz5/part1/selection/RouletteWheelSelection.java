package hr.fer.zemris.optjava.dz5.part1.selection;

import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * Roulette wheel selection. It gives advantage in selection to parents with
 * lower cost.
 */
public class RouletteWheelSelection implements ISelection {
	private Random random;

	public RouletteWheelSelection(Random random) {
		this.random = random;
	}

	@Override
	public Chromosome select(Set<Chromosome> population) {
		double minFitness = Double.MAX_VALUE;
		double sum = 0;
		for (Chromosome c : population) {
			if (c.fitness < minFitness) {
				minFitness = c.fitness;
			}
			sum += c.fitness;
		}

		sum -= population.size() * minFitness;
		double p = random.nextDouble();
		double pCurrent = 0;

		Chromosome last = null;
		for (Chromosome c : population) {
			pCurrent += (c.fitness - minFitness) / sum;
			if (pCurrent >= p) {
				return c;
			}
			last = c;
		}

		return last;
	}

}
