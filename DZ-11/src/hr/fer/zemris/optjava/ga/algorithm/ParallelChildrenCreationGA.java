package hr.fer.zemris.optjava.ga.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.optjava.art.GrayScaleImage;
import hr.fer.zemris.optjava.ga.generic.GASolution;
import hr.fer.zemris.optjava.ga.generic.GenerateChildJob;
import hr.fer.zemris.optjava.ga.generic.RouletteWheelSelection;
import hr.fer.zemris.optjava.ga.impl.DiscreteCrossover;
import hr.fer.zemris.optjava.ga.impl.Evaluator;
import hr.fer.zemris.optjava.ga.impl.IntArrayGASolution;
import hr.fer.zemris.optjava.ga.impl.MutationImpl;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class ParallelChildrenCreationGA {
	private int populationSize;
	private int rectanglesCount;
	private GrayScaleImage originalImage;
	private int maxGenerations;
	private volatile List<GASolution<int[]>> population;
	private int taskSize;
	private int colChange;
	private int posChange;
	private double minFitness;
	private double pMutation;
	private IRNG rng;

	public ParallelChildrenCreationGA(int populationSize, int rectanglesCount, GrayScaleImage originalImage,
			int maxGenerations, int taskSize, int colChange, int posChange, double minFitness,
			double pMutation) {
		super();
		this.populationSize = populationSize;
		this.rectanglesCount = rectanglesCount;
		this.originalImage = originalImage;
		this.maxGenerations = maxGenerations;
		this.taskSize = taskSize;
		this.colChange = colChange;
		this.posChange = posChange;
		this.minFitness = minFitness;
		this.pMutation = pMutation;
		this.rng = RNG.getRNG();
	}

	public GASolution<int[]> run() throws InterruptedException {
		population = generateInitialPopulation();
		LinkedBlockingQueue<Integer> tasks = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<GASolution<int[]>> children = new LinkedBlockingQueue<>();

		// Initialize worker threads
		int workersCount = Runtime.getRuntime().availableProcessors();
		EVOThread[] workers = new EVOThread[workersCount];
		for (int i = 0; i < workersCount; i++) {
			workers[i] = new EVOThread(new GenerateChildJob<>(tasks, children, new Evaluator(originalImage),
					new RouletteWheelSelection<>(), new DiscreteCrossover(),
					new MutationImpl(colChange, posChange, pMutation), population));
			workers[i].start();
		}

		int generation = 0;
		GASolution<int[]> best = population.get(0);
		do {
			generation++;

			// Assign tasks to worker threads
			int tmp = 0;
			while (tmp + taskSize < populationSize) {
				tasks.put(taskSize);
				tmp += taskSize;
			}
			tasks.put(populationSize - tmp);

			// Get new population
			List<GASolution<int[]>> newPopulation = new ArrayList<>();
			while (newPopulation.size() < populationSize) {
				newPopulation.add(children.take());
			}

			// Update population
			population.clear();
			population.addAll(newPopulation);

			// Find the best solution
			for (GASolution<int[]> sol : population) {
				if (sol.fitness > best.fitness) {
					best = sol;
				}
			}

			System.out.println(generation + " best: " + best.fitness);

		} while (generation < maxGenerations && best.fitness < minFitness);

		// Poison worker threads
		for (int i = 0; i < workersCount; i++) {
			tasks.add(0);
		}

		return best;
	}

	private List<GASolution<int[]>> generateInitialPopulation() {
		List<GASolution<int[]>> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			GASolution<int[]> sol = new IntArrayGASolution(1 + rectanglesCount * 5);
			sol.getData()[0] = rng.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);

			int index = 0;
			for (int j = 0; j < rectanglesCount; j++) {
				sol.getData()[index + 1] = rng.nextInt(0, originalImage.getWidth());
				sol.getData()[index + 2] = rng.nextInt(0, originalImage.getHeight());
				sol.getData()[index + 3] = rng.nextInt(1, originalImage.getWidth() / 4);
				sol.getData()[index + 4] = rng.nextInt(1, originalImage.getHeight() / 4);
				sol.getData()[index + 5] = rng.nextInt(0, 256);
				index += 5;
			}
			Evaluator evaluator = new Evaluator(originalImage);
			evaluator.evaluate(sol);
			population.add(sol);
		}
		return population;
	}
}
