package hr.fer.zemris.optjava.dz8.optimization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.neural_network.INeuralNetwork;
import hr.fer.zemris.optjava.dz8.optimization.strategies.IStrategy;

public class DifferentialEvolution implements IOptAlgorithm {
	private int popSize;
	private int dimension;
	private double[] bL;
	private double[] bU;
	private INeuralNetwork neuralNetwork;
	private int maxIterations;
	private IStrategy strategy;
	private double merr;

	public DifferentialEvolution(int popSize, int dimension, double[] bL, double[] bU, INeuralNetwork neuralNetwork,
			int maxIterations, IStrategy strategy, double merr) {
		super();
		this.popSize = popSize;
		this.dimension = dimension;
		this.bL = bL;
		this.bU = bU;
		this.neuralNetwork = neuralNetwork;
		this.maxIterations = maxIterations;
		this.strategy = strategy;
		this.merr = merr;
	}

	@Override
	public double[] run() {
		Random random = new Random();
		// Create initial population
		List<Vector> population = new ArrayList<>();
		for (int i = 0; i < popSize; i++) {
			double[] x = new double[dimension];
			for (int j = 0; j < dimension; j++) {
				x[j] = bL[j] + random.nextDouble() * (bU[j] - bL[j]);
			}
			population.add(new Vector(x));
		}
		// Evaluate population
		for (Vector v : population) {
			v.setError(neuralNetwork.getError(v.values));
		}

		double minError = Double.MAX_VALUE;
		Vector best = null;

		int iter = 0;
		do {
			iter++;
			List<Vector> newPopulation = new ArrayList<>();
			// Generate mutant vector
			for (int i = 0; i < popSize; i++) {
				Vector target = population.get(i);
				Vector trial = strategy.generateTrial(population, i);

				// Add trial to new population if it is not worse than target
				trial.setError(neuralNetwork.getError(trial.values));
				if (trial.getError() <= target.getError()) {
					newPopulation.add(trial);
				} else {
					newPopulation.add(target);
				}
			}
			population = newPopulation;

			// Find the best vector
			minError = Double.MAX_VALUE;
			for (Vector v : population) {
				if (v.getError() < minError) {
					minError = v.getError();
					best = new Vector(Arrays.copyOf(v.values, v.values.length));
				}
			}

			System.out.println("iteration: " + iter + " error: " + minError);
		} while (iter < maxIterations && minError > merr);

		return best.values;
	}

}
