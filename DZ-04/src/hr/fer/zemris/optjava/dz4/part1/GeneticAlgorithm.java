package hr.fer.zemris.optjava.dz4.part1;

import java.io.IOException;
import java.util.Random;

public class GeneticAlgorithm {
	public static void main(String[] args) throws IOException {
		if (args.length != 5) {
			System.out.println("5 arguments expected:\n"
					+ "population size, minimal error value, maximal number of generations, selection type, sigma");
			System.exit(0);
		}

		final int POP_SIZE = Integer.parseInt(args[0]);
		final double MIN_ERROR = Double.parseDouble(args[1]);
		final int MAX_GEN = Integer.parseInt(args[2]);
		String selectionType = args[3];
		final double sigma = Double.parseDouble(args[4]);

		final int VARS_NUM = 6;
		final int NUMBER_OF_ELITE = 2;
		final double ALPHA = 0.02;

		Random random = new Random();

		ISelection selection;
		if (selectionType.equals("rouletteWheel")) {
			selection = new RouletteWheelSelection(random);
		} else {
			String[] tmp = selectionType.split(":");
			int n = Integer.parseInt(tmp[1]);
			selection = new TournamentSelection(random, n, true);
		}

		BLXCrossover crossover = new BLXCrossover(random, ALPHA);
		IFunction function = ErrorFunction.readFromFile("zad-prijenosna.txt");
		Mutation mutation = new Mutation(random, sigma);

		Population population = new Population();
		Population newPopulation = new Population();

		while (population.size() < POP_SIZE) {
			Chromosome c = new Chromosome(VARS_NUM);
			c.randomize(-5, 5);
			population.add(c);
		}
		population.evaluate(function, true);

		int generation = 0;

		while (generation < MAX_GEN && population.getLowestError() > MIN_ERROR) {
			generation++;

			newPopulation.add(population.getBest(NUMBER_OF_ELITE));

			while (newPopulation.size() < POP_SIZE) {
				Chromosome firstParent = selection.select(population);
				Chromosome secondParent = selection.select(population);
				Chromosome child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				newPopulation.add(child);
			}

			population = newPopulation;
			newPopulation = new Population();
			population.evaluate(function, true);
			System.out.println("error: " + population.getLowestError());

		}

		System.out.println("Solution: " + population.getBest());

	}
}
