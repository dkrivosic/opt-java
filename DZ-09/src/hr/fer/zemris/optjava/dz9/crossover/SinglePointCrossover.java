package hr.fer.zemris.optjava.dz9.crossover;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class SinglePointCrossover implements ICrossover {
	private Random random;

	public SinglePointCrossover() {
		this.random = new Random();
	}
	
	@Override
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = firstParent.solution.length;
		int point = random.nextInt(n - 2) + 1;
		Chromosome child = new Chromosome();
		child.solution = new double[n];
		child.objective = new double[firstParent.objective.length];
		for (int i = 0; i < point; i++) {
			child.solution[i] = firstParent.solution[i];
		}
		for (int i = point; i < n; i++) {
			child.solution[i] = secondParent.solution[i];
		}
		return child;
	}

}
