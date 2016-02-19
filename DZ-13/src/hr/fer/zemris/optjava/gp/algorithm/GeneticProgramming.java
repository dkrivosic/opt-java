package hr.fer.zemris.optjava.gp.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.gp.ant.Ant;
import hr.fer.zemris.optjava.gp.crossover.ICrossover;
import hr.fer.zemris.optjava.gp.helpers.TreeGenerator;
import hr.fer.zemris.optjava.gp.mutation.IMutation;
import hr.fer.zemris.optjava.gp.selection.ISelection;

public class GeneticProgramming implements IOPTAlgorithm {
	private int populationSize;
	private int maxInitialDepth;
	private TreeGenerator treeGenerator;
	private int maxIterations;
	private double acceptableFitness;
	private AntEvaluator evaluator;
	private double pMutation;
	private double pReproduction;
	private Random random;
	private ISelection selection;
	private IMutation mutation;
	private ICrossover crossover;
	private int maxNodes;

	public GeneticProgramming(int populationSize, int maxInitialDepth, TreeGenerator treeGenerator, int maxIterations,
			double acceptableFitness, AntEvaluator evaluator, double pMutation, double pReproduction, Random random,
			ISelection selection, IMutation mutation, ICrossover crossover, int maxNodes) {
		super();
		this.populationSize = populationSize;
		this.maxInitialDepth = maxInitialDepth;
		this.treeGenerator = treeGenerator;
		this.maxIterations = maxIterations;
		this.acceptableFitness = acceptableFitness;
		this.evaluator = evaluator;
		this.pMutation = pMutation;
		this.pReproduction = pReproduction;
		this.random = random;
		this.selection = selection;
		this.mutation = mutation;
		this.crossover = crossover;
		this.maxNodes = maxNodes;
	}

	@Override
	public Ant run() {
		int maxMoves = evaluator.getMaxMoves();
		// Initial population
		List<Ant> population = new ArrayList<>();
		while (population.size() < populationSize) {
			for (int depth = 2; depth <= maxInitialDepth; depth++) {
				Ant a = new Ant(treeGenerator.generateFull(depth), maxMoves);
				a.setParentFoodEaten(0);
				population.add(a);
				if (population.size() == populationSize)
					break;

				a = new Ant(treeGenerator.generateGrow(depth), maxMoves);
				a.setParentFoodEaten(0);
				population.add(a);
				if (population.size() == populationSize)
					break;
			}
		}

		// Evaluate initial population
		for (Ant ant : population) {
			evaluator.evaluate(ant);
		}

		int iter = 0;
		Ant best = population.get(0);
		do {
			iter++;
			System.out.println("iteration: " + iter + " best: " + best.getFitness());
			List<Ant> newPopulation = new ArrayList<>();
			newPopulation.add(best.copy());

			// Generate new population
			while (newPopulation.size() < populationSize) {
				double p = random.nextDouble();
				double pSum = pReproduction;
				if (p < pSum) { // Reproduction
					Ant ant = selection.select(population);
					newPopulation.add(ant);
				} else {
					pSum += pMutation;
					if (p < pSum) { // Mutation
						Ant ant = selection.select(population);
						mutation.mutate(ant);
						if (ant.getNumberOfNodes() < maxNodes) {
							newPopulation.add(ant);
						}
					} else { // Crossover
						Ant firstParent = selection.select(population);
						Ant secondParent = selection.select(population);
						List<Ant> children = crossover.crossover(firstParent, secondParent);
						for (Ant ant : children) {
							if (ant.getNumberOfNodes() < maxNodes) {
								newPopulation.add(ant);
							}
						}
					}
				}
			}

			// Evaluate new population
			for (Ant ant : newPopulation) {
				evaluator.evaluate(ant);
			}

			// Update best
			for (Ant ant : newPopulation) {
				if (ant.getFitness() > best.getFitness()) {
					best = ant;
				}
			}

			population = newPopulation;
		} while (iter < maxIterations && best.getFitness() < acceptableFitness);

		for (Ant ant : population) {
			if (ant.getFitness() > best.getFitness()) {
				best = ant;
			}
		}

		System.out.println("best: " + best.getFitness());

		return best;
	}

}
