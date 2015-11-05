package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.crossover.ICrossoverOperation;
import hr.fer.zemris.optjava.dz5.part2.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.part2.selection.ISelection;

/**
 * Offspring selection genetic algorithm.
 */
public class OffspringSelectionGA {
	private int maxIterations;
	private double maxSelPress;
	private ISelection firstSelection;
	private ISelection secondSelection;
	private ICrossoverOperation crossover;
	private IMutation mutation;
	private CostFunction function;
	private double succRatio;
	private CompFactorPlan compFactorPlan;

	public OffspringSelectionGA(int maxIterations, double maxSelPress, double succRatio, ISelection firstSelection,
			ISelection secondSelection, ICrossoverOperation crossover, IMutation mutation, CostFunction function,
			CompFactorPlan compFactorPlan) {

		this.maxIterations = maxIterations;
		this.maxSelPress = maxSelPress;
		this.firstSelection = firstSelection;
		this.secondSelection = secondSelection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.function = function;
		this.succRatio = succRatio;
		this.compFactorPlan = compFactorPlan;
	}

	public Set<Chromosome> run(Set<Chromosome> population) {
		int iter = 0;
		double actSelPress = 0;
		compFactorPlan.reset();
		double compFactor = compFactorPlan.getCompFactor();
		final int popSize = population.size();
		Random random = new Random();

		while (iter < maxIterations && actSelPress < maxSelPress) {
			Set<Chromosome> newPopulation = new HashSet<>();
			Set<Chromosome> pool = new HashSet<>();

			while ((newPopulation.size() < population.size() * succRatio)
					&& ((newPopulation.size() + pool.size()) < population.size() * maxSelPress)) {

				Chromosome firstParent = firstSelection.select(population);
				Chromosome secondParent = secondSelection.select(population);
				if (secondParent.getCost() < firstParent.getCost()) {
					Chromosome tmp = firstParent;
					firstParent = secondParent;
					secondParent = tmp;
				}
				Chromosome child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				child.evaluate(function);

				if (child.getCost() < secondParent.getCost()
						- (secondParent.getCost() - firstParent.getCost()) * compFactor) {
					newPopulation.add(child);
				} else {
					pool.add(child);
				}
			}

			actSelPress = (double) (newPopulation.size() + pool.size()) / population.size();

			List<Chromosome> poolList = new ArrayList<>(pool);

			while (newPopulation.size() < popSize && !poolList.isEmpty()) {
				int index = random.nextInt(poolList.size());
				newPopulation.add(poolList.get(index));
				poolList.remove(index);
			}

			// if there is not enough chromosomes in the pool
			while (newPopulation.size() - popSize < 0) {
				Chromosome firstParent = firstSelection.select(population);
				Chromosome secondParent = secondSelection.select(population);
				Chromosome child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				newPopulation.add(child);
			}

			compFactor = compFactorPlan.getCompFactor();
			iter++;
			population = newPopulation;
		}

		return population;
	}
}
