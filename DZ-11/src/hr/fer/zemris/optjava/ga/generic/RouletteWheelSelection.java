package hr.fer.zemris.optjava.ga.generic;

import java.util.List;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Roulette wheel selection.
 */
public class RouletteWheelSelection<T> implements ISelection<T> {
	private IRNG rng;

	public RouletteWheelSelection() {
		this.rng = RNG.getRNG();
	}

	@Override
	public GASolution<T> select(List<GASolution<T>> population) {
		double min = Double.MAX_VALUE;
		for (GASolution<T> sol : population) {
			if (sol.fitness < min) {
				min = sol.fitness;
			}
		}

		double sum = 0;
		for (GASolution<T> sol : population) {
			sum += sol.fitness - min;
		}

		double p = rng.nextDouble();
		double pp = 0;
		for (GASolution<T> sol : population) {
			pp += (sol.fitness - min) / sum;
			if (p < pp)
				return sol;
		}

		return population.get(population.size() - 1);
	}

}
