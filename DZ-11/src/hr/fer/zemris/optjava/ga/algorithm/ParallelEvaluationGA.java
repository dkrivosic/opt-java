package hr.fer.zemris.optjava.ga.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.generic.EvaluateJob;
import hr.fer.zemris.optjava.ga.generic.GASolution;
import hr.fer.zemris.optjava.ga.generic.ICrossover;
import hr.fer.zemris.optjava.ga.generic.IMutation;
import hr.fer.zemris.optjava.ga.generic.ISelection;
import hr.fer.zemris.optjava.ga.impl.Evaluator;
import hr.fer.zemris.optjava.ga.impl.IntArrayGASolution;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class ParallelEvaluationGA {
	private GrayScaleImage originalImage;
	private int rectanglesNumber;
	private int populationSize;
	private int maxGenerations;
	private double minFitness;
	private IRNG rng;
	private ISelection<int[]> selection;
	private ICrossover<int[]> crossover;
	private IMutation<int[]> mutation;

	public ParallelEvaluationGA(GrayScaleImage originalImage, int rectanglesNumber, int populationSize,
			int maxGenerations, double minFitness, ISelection<int[]> selection, ICrossover<int[]> crossover,
			IMutation<int[]> mutation) {
		super();
		this.originalImage = originalImage;
		this.rectanglesNumber = rectanglesNumber;
		this.populationSize = populationSize;
		this.maxGenerations = maxGenerations;
		this.minFitness = minFitness;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.rng = RNG.getRNG();
	}

	public GASolution<int[]> run() throws IOException, InterruptedException {
		List<GASolution<int[]>> population = generateInitialPopulation();
		LinkedBlockingQueue<GASolution<int[]>> nonEvaluated = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<GASolution<int[]>> evaluated = new LinkedBlockingQueue<>();

		// Initialize worker threads
		int workersCount = Runtime.getRuntime().availableProcessors();
		EVOThread[] workers = new EVOThread[workersCount];
		for (int i = 0; i < workersCount; i++) {
			workers[i] = new EVOThread(new EvaluateJob<>(nonEvaluated, evaluated, new Evaluator(originalImage)));
			workers[i].start();
		}

		// Evaluate initial population
		for (GASolution<int[]> solution : population) {
			nonEvaluated.put(solution);
		}
		population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(evaluated.take());
		}

		int generation = 0;
		GASolution<int[]> best = population.get(0);
		do {
			generation++;

			// Create new generation
			List<GASolution<int[]>> newPopulation = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				GASolution<int[]> firstParent = selection.select(population);
				GASolution<int[]> secondParent = selection.select(population);
				GASolution<int[]> child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				newPopulation.add(child);
			}

			// Evaluate new Generation
			for (GASolution<int[]> solution : newPopulation) {
				nonEvaluated.put(solution);
			}
			population = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				population.add(evaluated.take());
			}

			// Find the best solution
			for (GASolution<int[]> sol : population) {
				if (sol.fitness > best.fitness) {
					best = sol;
				}
			}

			System.out.println(generation + " best: " + best.fitness);

		} while (generation < maxGenerations && best.fitness < minFitness);

		// Stop worker threads
		for (int i = 0; i < workersCount; i++) {
			GASolution<int[]> poisonPill = new IntArrayGASolution(null);
			nonEvaluated.add(poisonPill);
		}

		return best;
	}

	private List<GASolution<int[]>> generateInitialPopulation() {
		List<GASolution<int[]>> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			GASolution<int[]> sol = new IntArrayGASolution(1 + rectanglesNumber * 5);
			sol.getData()[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);

			int index = 0;
			for (int j = 0; j < rectanglesNumber; j++) {
				sol.getData()[index + 1] = rng.nextInt(0, originalImage.getWidth());
				sol.getData()[index + 2] = rng.nextInt(0, originalImage.getHeight());
				sol.getData()[index + 3] = rng.nextInt(1, originalImage.getWidth() / 4);
				sol.getData()[index + 4] = rng.nextInt(1, originalImage.getHeight() / 4);
				sol.getData()[index + 5] = rng.nextInt(0, 256);
				index += 5;
			}
			population.add(sol);
		}
		return population;
	}
}
