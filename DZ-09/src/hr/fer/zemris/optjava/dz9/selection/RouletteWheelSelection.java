package hr.fer.zemris.optjava.dz9.selection;

import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class RouletteWheelSelection implements ISelection {
	private Random random;

	public RouletteWheelSelection() {
		this.random = new Random();
	}

	@Override
	public Chromosome select(List<Chromosome> population) {
		double totalFitness = 0;
		double minFitness = Double.MAX_VALUE;
		for (Chromosome c : population) {
			totalFitness += c.getFitness();
			if(c.getFitness() < minFitness) {
				minFitness = c.getFitness();
			}
		}
		
		totalFitness -= population.size() * minFitness;

		double pRand = random.nextDouble();
		double pSum = 0;

		for (Chromosome c : population) {
			pSum += (c.getFitness() - minFitness) / totalFitness;
			if (pRand < pSum) {
				return c;
			}
		}

		return population.get(population.size() - 1);
	}

}
