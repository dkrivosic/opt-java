package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part1.comparison.ChangingCompFactor;
import hr.fer.zemris.optjava.dz5.part1.comparison.ConstantCompFactor;
import hr.fer.zemris.optjava.dz5.part1.comparison.ICompFactorPlan;
import hr.fer.zemris.optjava.dz5.part1.crossover.ICrossoverOperation;
import hr.fer.zemris.optjava.dz5.part1.crossover.OnePointCrossover;
import hr.fer.zemris.optjava.dz5.part1.crossover.TwoPointCrossover;
import hr.fer.zemris.optjava.dz5.part1.crossover.UniformCrossover;
import hr.fer.zemris.optjava.dz5.part1.selection.ISelection;
import hr.fer.zemris.optjava.dz5.part1.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.part1.selection.RouletteWheelSelection;

/**
 * RAPGA used for maximizing fitness function.
 */
public class GeneticAlgorithm {

	public static void main(String[] args) {
		if (args.length < 5) {
			System.out.println("arguments expected:"
					+ " min population size, max population size, max selection pressure, compFactor change, max effort");
			System.exit(0);
		}

		final int minPop = Integer.parseInt(args[0]);
		final int maxPop = Integer.parseInt(args[1]);
		final double maxSelPress = Integer.parseInt(args[2]);
		final boolean cfc = Boolean.parseBoolean(args[3]);
		final int chromosomeSize = 1000;
		Random random = new Random();
		Function function = new Function();

		ICompFactorPlan plan;
		if (cfc) {
			plan = new ChangingCompFactor();
		} else {
			plan = new ConstantCompFactor(0);
		}

		List<ICrossoverOperation> crossovers = new ArrayList<>();
		crossovers.add(new OnePointCrossover(random));
		crossovers.add(new TwoPointCrossover(random));
		crossovers.add(new UniformCrossover(random));

		List<Mutation> mutations = new ArrayList<>();
		mutations.add(new Mutation(random, 0.05));
		mutations.add(new Mutation(random, 0.01));

		ISelection firstSelection = new RouletteWheelSelection(random);
		ISelection secondSelection = new RandomSelection(random);

		int n = (maxPop + minPop) / 2;
		Set<Chromosome> population = new HashSet<>();
		while (population.size() < n) { // Generating initial population
			Chromosome c = new Chromosome(chromosomeSize);
			c.randomize(random);
			c.fitness = function.valueAt(c);
			population.add(c);
		}

		double actSelPress = 0;
		final int maxEffort = Integer.parseInt(args[4]);
		final int maxGen = 10000;
		int gen = 0;

		while (actSelPress < maxSelPress && gen < maxGen) {
			gen++;
			Set<Chromosome> newPopulation = new HashSet<>();
			Set<Chromosome> pool = new HashSet<>();
			int effort = 0;
			while (effort < maxEffort && newPopulation.size() < maxPop) {
				Chromosome firstParent = firstSelection.select(population);
				Chromosome secondParent = secondSelection.select(population);
				double compFactor = plan.getCompFactor();

				// If first parent is better, switch them
				if (firstParent.fitness > secondParent.fitness) {
					Chromosome tmp = firstParent;
					firstParent = secondParent;
					secondParent = tmp;
				}

				actSelPress = 0;

				for (ICrossoverOperation cross : crossovers) {
					List<Chromosome> children = cross.crossover(firstParent, secondParent);
					for (Chromosome child : children) {
						for (Mutation mutation : mutations) {
							Chromosome c = child.copy();
							mutation.mutate(c);
							c.fitness = function.valueAt(c);
							double f1 = firstParent.fitness;
							double f2 = secondParent.fitness;
							if (newPopulation.size() >= maxPop) {
								break;
							} else if (c.fitness >= f1 + compFactor * (f2 - f1)) {
								newPopulation.add(c);
							} else {
								pool.add(c);
							}
						}
					}

				}
			}

			actSelPress = (newPopulation.size() * pool.size()) / population.size();

			// If population is smaller than minPop add from pool
			if (newPopulation.size() < minPop) {
				int x = newPopulation.size();
				List<Chromosome> ls = new ArrayList<>(pool);
				for (int i = 0; i < minPop - x; i++) {
					newPopulation.add(ls.get(random.nextInt(pool.size())));
				}
			}
			population = newPopulation;
		}

		List<Chromosome> pop = new ArrayList<>(population);
		Collections.sort(pop);

		Chromosome solution = pop.get(0);
		System.out.println("solution = " + solution);
		System.out.println("fitness = " + solution.fitness);
	}
}
