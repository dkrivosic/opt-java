package hr.fer.zemris.optjava.dz4.part2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BoxFilling {

	public static void main(String[] args) throws IOException {
		if (args.length != 8) {
			System.out.println(
					"8 arguments expected: path, population size, n, m, p, max iterations, acceptable container length, mutation probability");
			System.exit(0);
		}

		String path = args[0];
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String line = reader.readLine();
		line = line.replaceAll("[\\[\\] ]", "");
		String[] stickStrings = line.split(",");
		reader.close();

		Stick[] sticks = new Stick[stickStrings.length];
		for (int i = 0; i < stickStrings.length; i++) {
			sticks[i] = new Stick(i, Integer.parseInt(stickStrings[i]));
		}

		int populationSize = Integer.parseInt(args[1]);
		int n = Integer.parseInt(args[2]);
		int m = Integer.parseInt(args[3]);
		boolean p = Boolean.parseBoolean(args[4]);
		int maxIterations = Integer.parseInt(args[5]);
		int acceptableLength = Integer.parseInt(args[6]);
		double mutationP = Double.parseDouble(args[7]);

		FitnessFunction function = new FitnessFunction();
		Operators operators = new Operators(mutationP);

		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			Chromosome c = new Chromosome(sticks);
			c.randomize();
			c.fitness = function.evaluate(c);
			population.add(c);
		}

		NTournamentSelection nTournament = new NTournamentSelection(n);
		NTournamentSelection mTournament = new NTournamentSelection(m);

		int iter = 0;
		do {
			iter++;
			Chromosome firstParent = nTournament.select(population, true);
			Chromosome secondParent = nTournament.select(population, true);
			Chromosome child = operators.crossover(firstParent, secondParent);
			operators.mutate(child);
			child.fitness = function.evaluate(child);

			Chromosome bad = mTournament.select(population, false);
			if (p) {
				if (child.fitness > bad.fitness) {
					population.remove(bad);
					population.add(child);
				}

			} else {
				population.remove(bad);
				population.add(child);
			}

			if (getShortest(population).places.length <= acceptableLength) {
				break;
			}

		} while (iter < maxIterations);

		Chromosome solution = getShortest(population);
		System.out.println(solution);
		System.out.println("container length = " + solution.places.length);
	}

	private static Chromosome getShortest(List<Chromosome> pop) {
		int shortest = Integer.MAX_VALUE;
		Chromosome best = null;
		for (Chromosome c : pop) {
			if (c.places.length < shortest) {
				shortest = c.places.length;
				best = c;
			}
		}
		return best;
	}

}
