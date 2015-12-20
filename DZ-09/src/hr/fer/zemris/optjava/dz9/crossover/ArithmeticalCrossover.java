package hr.fer.zemris.optjava.dz9.crossover;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class ArithmeticalCrossover implements ICrossover {
	private double lambda;

	public ArithmeticalCrossover(double lambda) {
		this.lambda = lambda;
	}

	@Override
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		Chromosome child = new Chromosome();
		child.solution = new double[firstParent.solution.length];
		child.objective = new double[firstParent.objective.length];

		for (int i = 0; i < child.solution.length; i++) {
			child.solution[i] = lambda * firstParent.solution[i] + (1 - lambda) * secondParent.solution[i];
		}
		return child;
	}

}
