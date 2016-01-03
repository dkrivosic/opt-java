package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.crossover.ICrossover;
import hr.fer.zemris.optjava.dz9.functions.InRange;
import hr.fer.zemris.optjava.dz9.moop.Evaluator;
import hr.fer.zemris.optjava.dz9.moop.MOOPProblem;
import hr.fer.zemris.optjava.dz9.mutation.IMutation;
import hr.fer.zemris.optjava.dz9.selection.ISelection;

public class NSGAII implements IMOOPAlgorithm {
	private int populationSize;
	private InRange hardConstraints;
	private Random random;
	private MOOPProblem moop;
	private Evaluator evaluator;
	private int maxIterations;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;

	public NSGAII(int populationSize, InRange hardConstraints, MOOPProblem moop, Evaluator evaluator, int maxIterations,
			ISelection selection, ICrossover crossover, IMutation mutation) {
		this.populationSize = populationSize;
		this.hardConstraints = hardConstraints;
		this.moop = moop;
		this.evaluator = evaluator;
		this.maxIterations = maxIterations;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.random = new Random();
	}

	@Override
	public List<Chromosome> run() {
		// Create initial population
		List<Chromosome> population = new ArrayList<>();
		while (population.size() < populationSize) {
			population.add(generateRandomChromosome());
		}
		
		evaluator.evaluatePopulation(population);

		int iter = 0;
		while (iter < maxIterations) {
			iter++;

			// Generate children
			List<Chromosome> children = new ArrayList<>();
			while (children.size() < populationSize) {
				Chromosome firstParent = selection.select(population);
				Chromosome secondParent = selection.select(population);
				Chromosome child = crossover.crossover(firstParent, secondParent);
				child = mutation.mutate(child);
				if (hardConstraints.satisfied(child.solution)) {
					children.add(child);
				}
			}
			evaluator.evaluatePopulation(children);

			// Generate new population
			population.addAll(children);
			List<List<Chromosome>> fronts = evaluator.nonDominatedSort(population);
			List<Chromosome> newPopulation = new ArrayList<>();

			int currentFront = 0;
			while (newPopulation.size() + fronts.get(currentFront).size() <= populationSize) {
				newPopulation.addAll(fronts.get(currentFront));
				currentFront++;
			}

			// crowding sort
			List<Chromosome> front = fronts.get(currentFront);
			evaluator.crowdingSort(front);
			int index = 0;
			while (newPopulation.size() < populationSize) {
				newPopulation.add(front.get(index));
				index++;
			}

			population = newPopulation;
		}

		return population;

	}

	private Chromosome generateRandomChromosome() {
		Chromosome c = new Chromosome();
		int n = moop.getSolutionSize();
		int m = moop.getNumberOfObjectives();
		c.solution = new double[n];
		c.objective = new double[m];
		for (int i = 0; i < n; i++) {
			c.solution[i] = random.nextDouble() * (hardConstraints.xmax[i] - hardConstraints.xmin[i])
					+ hardConstraints.xmin[i];
		}
		return c;
	}

}
