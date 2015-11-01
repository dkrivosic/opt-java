package hr.fer.zemris.optjava.dz4.part1;

import java.util.List;
import java.util.Random;

public class RouletteWheelSelection implements ISelection {
	private Random random;

	public RouletteWheelSelection(Random random) {
		this.random = random;
	}

	@Override
	public Chromosome select(Population population) {
		int n = population.size();
		List<Chromosome> chromosomes = population.getAll();
		double minFit = Double.MAX_VALUE;
		double sumFit = 0;

		for (int i = 0; i < n; i++) {
			double fit = chromosomes.get(i).fitness;
			sumFit += fit;
			if (fit < minFit) {
				minFit = fit;
			}
		}
		sumFit -= n * minFit;

		double p = random.nextDouble();
		double pSum = 0;

		for (int i = 0; i < n; i++) {
			pSum += (chromosomes.get(i).fitness - minFit) / sumFit;
			if (pSum > p) {
				return chromosomes.get(i);
			}
		}
		return chromosomes.get(n - 1); // if p == 1.0
	}

}
