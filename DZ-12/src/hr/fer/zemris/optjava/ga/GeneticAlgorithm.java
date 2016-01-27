package hr.fer.zemris.optjava.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.optjava.bool.BooleanExpressionSolver;
import hr.fer.zemris.optjava.ga.operators.abs.ICrossover;
import hr.fer.zemris.optjava.ga.operators.abs.IMutation;
import hr.fer.zemris.optjava.ga.operators.abs.ISelection;
import hr.fer.zemris.optjava.ga.struct.Chromosome;
import hr.fer.zemris.optjava.ga.struct.Evaluator;

public class GeneticAlgorithm extends IOptAlgorithm {
	private int maxGenerations;
	private int populationSize;
	private int numberOfCLBInputs;
	private int numberOfCLBs;
	private BooleanExpressionSolver solver;
	private Evaluator evaluator;
	private LinkedBlockingQueue<Chromosome> outQueue;
	private LinkedBlockingQueue<Chromosome> inQueue;
	private LinkedBlockingQueue<Chromosome> solution;
	private ISelection selection;
	private ICrossover crossover;
	private IMutation mutation;
	/**
	 * Frequency of sending the best solution to other algorithm (in
	 * iterations).
	 */
	private final int frequency;

	public GeneticAlgorithm(int maxGenerations, int populationSize, int numberOfCLBInputs, int numberOfCLBs,
			BooleanExpressionSolver solver, Evaluator evaluator, LinkedBlockingQueue<Chromosome> outQueue,
			LinkedBlockingQueue<Chromosome> inQueue, LinkedBlockingQueue<Chromosome> solution, ISelection selection,
			ICrossover crossover, IMutation mutation, int frequency) {
		super();
		this.maxGenerations = maxGenerations;
		this.populationSize = populationSize;
		this.numberOfCLBInputs = numberOfCLBInputs;
		this.numberOfCLBs = numberOfCLBs;
		this.solver = solver;
		this.evaluator = evaluator;
		this.outQueue = outQueue;
		this.inQueue = inQueue;
		this.solution = solution;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.frequency = frequency;
	}

	@Override
	public void run() {
		// Generate initial population
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			Chromosome c = new Chromosome(numberOfCLBInputs, numberOfCLBs);
			c.randomize(solver.getNumberOfVariables());
			population.add(c);
		}

		// Evaluate initial population
		for (Chromosome c : population) {
			evaluator.evaluate(c);
		}

		int generation = 0;
		boolean bestChanged = true;
		Chromosome best = population.get(0);
		do {
			generation++;

			// Exchange Chromosomes with other algorithms
			if ((generation % frequency == 0) && bestChanged) {
				try {
					outQueue.put(best);
				} catch (InterruptedException e) {
					System.err.println("Interrupted");
					System.exit(1);
				}
				bestChanged = false;
			}

			List<Chromosome> newPopulation = new ArrayList<>();
			Chromosome retrieved = inQueue.poll();
			if (retrieved != null) {
				newPopulation.add(retrieved);
			}

			// Create new population
			while (newPopulation.size() < populationSize) {
				Chromosome firstParent = selection.select(population);
				Chromosome secondParent = selection.select(population);
				Chromosome child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				evaluator.evaluate(child);
				newPopulation.add(child);
			}

			// Update best
			for (Chromosome c : newPopulation) {
				if (c.error < best.error) {
					best = c;
					bestChanged = true;
				}
			}

			if (best.error == 0) {
				try {
					solution.put(best);
				} catch (InterruptedException ignorable) {
				}
				finished = true;
			}

			population = newPopulation;
		} while (generation < maxGenerations && !finished);

		try {
			solution.put(best);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
