package hr.fer.zemris.optjava.dz5.part2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.crossover.ICrossoverOperation;
import hr.fer.zemris.optjava.dz5.part2.crossover.PositionBasedCrossover;
import hr.fer.zemris.optjava.dz5.part2.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.part2.mutation.SwapMutation;
import hr.fer.zemris.optjava.dz5.part2.selection.ISelection;
import hr.fer.zemris.optjava.dz5.part2.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.part2.selection.RouletteWheelSelection;

/**
 * SASEGASA for solving the quadratic assignment problem.
 */
public class GeneticAlgorithm {

	public static void main(String[] args) throws FileNotFoundException {
		Random random = new Random();
		CostFunction function = CostFunction.readFromFile(args[0]);
		int n = function.getN();
		ICrossoverOperation crossover = new PositionBasedCrossover(random, function);
		IMutation mutation = new SwapMutation();
		ISelection firstSelection = new RouletteWheelSelection(random);
		ISelection secondSelection = new RandomSelection(random);
		CompFactorPlan compFactorPlan = new CompFactorPlan(0, 1, 0.01);

		final int maxIterations = 100;
		final double maxSelPress = 300;
		final double succRatio = 0.4;
		int totalPopSize = 1000;
		int numberOfPop = 10;

		OffspringSelectionGA os = new OffspringSelectionGA(maxIterations, maxSelPress, succRatio, firstSelection,
				secondSelection, crossover, mutation, function, compFactorPlan);

		List<Chromosome> allChromosomes = new ArrayList<>();
		while (allChromosomes.size() < totalPopSize) {
			Chromosome cr = new Chromosome(n);
			cr.randomize();
			cr.evaluate(function);
			allChromosomes.add(cr);
		}

		while (numberOfPop > 0) {
			int popSize = totalPopSize / numberOfPop;
			List<Chromosome> TmpAll = new ArrayList<>();
			int startIndex = 0;
			for (int i = 0; i < numberOfPop; i++) {
				Set<Chromosome> population;
				if (totalPopSize - startIndex < 2 * popSize) {
					population = new HashSet<>(allChromosomes.subList(startIndex, totalPopSize));
				} else {
					population = new HashSet<>(allChromosomes.subList(startIndex, startIndex + popSize));
					startIndex += popSize;
				}
				TmpAll.addAll(os.run(population));
			}
			allChromosomes = TmpAll;
			TmpAll = new ArrayList<>();
			numberOfPop--;
		}

		Collections.sort(allChromosomes);
		Chromosome solution = allChromosomes.get(0);
		System.out.println("solution = " + solution);
		System.out.println("cost = " + solution.getCost());
	}
}
