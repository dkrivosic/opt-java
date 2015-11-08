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
		if (args.length != 8) {
			System.out.println("8 arguments expected:\npath to file with problem description"
					+ "\nTotal number of chromosomes \nInitial number of populations"
					+ "\nsuccess ratio \nmax selection pressure \nmax iterations"
					+ "\nConstant comparison factor? (true/false) \nconstant factor or delta");
			System.exit(0);
		}

		Random random = new Random();
		CostFunction function = CostFunction.readFromFile(args[0]);
		int n = function.getN();
		ICrossoverOperation crossover = new PositionBasedCrossover(random, function);
		IMutation mutation = new SwapMutation();
		ISelection firstSelection = new RouletteWheelSelection(random);
		ISelection secondSelection = new RandomSelection(random);
		boolean constant = Boolean.parseBoolean(args[6]);
		double comp = Double.parseDouble(args[7]);
		CompFactorPlan compFactorPlan;
		if (constant) {
			compFactorPlan = new CompFactorPlan(comp);
		} else {
			compFactorPlan = new CompFactorPlan(0, 1, comp);
		}

		final int maxIterations = Integer.parseInt(args[5]);
		final double maxSelPress = Integer.parseInt(args[4]);
		final double succRatio = Double.parseDouble(args[3]);
		int totalPopSize = Integer.parseInt(args[1]);
		int numberOfPop = Integer.parseInt(args[2]);

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
