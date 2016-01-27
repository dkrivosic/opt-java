package hr.fer.zemris.optjava.ga.operators.impl;

import hr.fer.zemris.optjava.ga.operators.abs.ICrossover;
import hr.fer.zemris.optjava.ga.struct.Chromosome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class DiscreteCrossover implements ICrossover {
	private IRNG rng;

	public DiscreteCrossover() {
		this.rng = RNG.getRNG();
	}

	@Override
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		Chromosome child = new Chromosome(firstParent.getNumberOfCLBInputs(), firstParent.getNumberOfCLBs());
		int n = firstParent.getData().length;

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
