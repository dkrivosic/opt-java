package hr.fer.zemris.optjava.ga.impl;

import hr.fer.zemris.optjava.ga.generic.GASolution;
import hr.fer.zemris.optjava.ga.generic.ICrossover;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Discrete crossover. Child's i-th component is equal to first parent's i-th
 * component or second parent's i-th component with same probability.
 */
public class DiscreteCrossover implements ICrossover<int[]> {
	private IRNG rng;

	public DiscreteCrossover() {
		this.rng = RNG.getRNG();
	}

	@Override
	public GASolution<int[]> crossover(GASolution<int[]> firstParent, GASolution<int[]> secondParent) {
		int n = firstParent.getData().length;
		GASolution<int[]> child = new IntArrayGASolution(n);

		for (int i = 0; i < n; i++) {
			if (rng.nextBoolean()) {
				child.getData()[i] = firstParent.getData()[i];
			} else {
				child.getData()[i] = secondParent.getData()[i];
			}
		}

		return child;
	}

}
