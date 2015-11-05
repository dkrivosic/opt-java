package hr.fer.zemris.optjava.dz5.part2.selection;

import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

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
		double maxCost = 0;
		double sum = 0;
		for (Chromosome c : population) {
			if (c.getCost() > maxCost) {
				maxCost = c.getCost();
			}
			sum += c.getCost();
		}

		sum = population.size() * maxCost - sum;
		double p = random.nextDouble();
		double pCurrent = 0;

		Chromosome last = null;
		for (Chromosome c : population) {
			pCurrent += (maxCost - c.getCost()) / sum;
			if (pCurrent >= p) {
				return c;
			}
			last = c;
		}

		return last;
	}

}
