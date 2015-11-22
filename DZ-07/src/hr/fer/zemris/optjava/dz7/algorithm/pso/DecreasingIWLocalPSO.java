package hr.fer.zemris.optjava.dz7.algorithm.pso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.neural_network.INeuralNetwork;

/**
 * Decreasing inertia weight particle swarm optimization algorithm.
 */
public class DecreasingIWLocalPSO implements IOptAlgorithm {

	private int swarmSize;
	private int maxIterations;
	private int dimension;
	private double[] xmin;
	private double[] xmax;
	private double[] vmin;
	private double[] vmax;
	private INeuralNetwork neuralNetwork;
	private double merr;
	private double cIndividual;
	private double cSocial;
	private double wmax;
	private double wmin;
	private int neighboursNumber;

	public DecreasingIWLocalPSO(int swarmSize, int maxIterations, int dimension, double[] xmin, double[] xmax,
			double[] vmin, double[] vmax, INeuralNetwork neuralNetwork, double merr, double cIndividual, double cSocial,
			double wmin, double wmax, int neighboursNumber) {
		this.swarmSize = swarmSize;
		this.maxIterations = maxIterations;
		this.dimension = dimension;
		this.xmin = xmin;
		this.xmax = xmax;
		this.vmin = vmin;
		this.vmax = vmax;
		this.neuralNetwork = neuralNetwork;
		this.merr = merr;
		this.cIndividual = cIndividual;
		this.cSocial = cSocial;
		this.wmax = wmax;
		this.wmin = wmin;
		this.neighboursNumber = neighboursNumber;
	}

	@Override
	public double[] run() {
		Random random = new Random();
		InertiaWeight w = new InertiaWeight(wmin, wmax, maxIterations);

		List<Particle> particles = new ArrayList<>();
		for (int i = 0; i < swarmSize; i++) {
			double[] x = new double[dimension];
			double[] v = new double[dimension];
			Particle p = new Particle();
			for (int d = 0; d < dimension; d++) {
				x[d] = random.nextDouble() * 2 - 1;
				v[d] = random.nextDouble() * 2 - 1;
			}
			p.setV(v);
			p.setX(x);
			p.setPersonalBestValue(Double.MAX_VALUE);
			particles.add(p);
		}

		// Initialize local best for each neighbourhood
		List<LocalBest> localBest = new ArrayList<>();
		for (int i = 0; i < swarmSize; i++) {
			localBest.add(new LocalBest());
		}

		// Create swarm.
		List<List<Particle>> swarm = new ArrayList<>();
		for (int i = 0; i < swarmSize; i++) {
			List<Particle> neighbourhood = new ArrayList<>();
			neighbourhood.add(particles.get(i));
			for (int j = 1; j <= neighboursNumber; j++) {
				int left = (i - j);
				if (left < 0) {
					left = swarmSize - j;
				}
				int right = (i + j) % swarmSize;
				neighbourhood.add(particles.get(left));
				neighbourhood.add(particles.get(right));
			}
			swarm.add(neighbourhood);
		}

		// Initialize global best
		double globalBestValue = Double.MAX_VALUE;
		double[] globalBest = null;

		// Algorithm
		int iter = 0;
		int dots = maxIterations / 100;
		do {
			iter++;
			if (iter % dots == 0)
				System.out.print(".");
			// Evaluate swarm
			for (int i = 0; i < swarmSize; i++) {
				List<Particle> neighbourhood = swarm.get(i);
				double xerr = neuralNetwork.getError(neighbourhood.get(0).getX());
				neighbourhood.get(0).setError(xerr);
			}

			// Update personal and local best
			for (int i = 0; i < swarmSize; i++) {
				List<Particle> neighbourhood = swarm.get(i);
				for (Particle p : neighbourhood) {
					if (p.getError() < p.getPersonalBestValue()) {
						p.setPersonalBest(Arrays.copyOf(p.getX(), p.getX().length));
						p.setPersonalBestValue(p.getError());
					}
					if (p.getError() < localBest.get(i).getLocalBestValue()) {
						localBest.get(i).setLocalBestValue(p.getError());
						localBest.get(i).setLocalBest(Arrays.copyOf(p.getX(), p.getX().length));
					}
				}
			}

			// Update particle speed and position
			for (int i = 0; i < swarmSize; i++) {
				List<Particle> neighbourhood = swarm.get(i);
				Particle p = neighbourhood.get(0);
				double v[] = p.getV();
				double x[] = p.getX();
				double pBest[] = p.getPersonalBest();
				double lBest[] = localBest.get(i).getLocalBest();
				for (int d = 0; d < dimension; d++) {
					v[d] = w.getWeight(iter) * v[d] + cIndividual * random.nextDouble() * (pBest[d] - x[d])
							+ cSocial * random.nextDouble() * (lBest[d] - x[d]);
					v[d] = checkBorders(v[d], vmin[d], vmax[d]);
					x[d] = x[d] + v[d];
					x[d] = checkBorders(x[d], xmin[d], xmax[d]);
				}
			}

			// Update global best
			for (int i = 0; i < swarmSize; i++) {
				if (localBest.get(i).getLocalBestValue() < globalBestValue) {
					globalBestValue = localBest.get(i).getLocalBestValue();
					globalBest = localBest.get(i).getLocalBest();
				}
			}

		} while (iter < maxIterations && globalBestValue > merr);

		return globalBest;

	}

	private double checkBorders(double v, double min, double max) {
		if (v < min) {
			return min;
		} else if (v > max) {
			return max;
		}
		return v;
	}

	/**
	 * Class for storing best values for each neighbourhood.
	 */
	class LocalBest {
		private double localBestValue;
		private double[] localBest;

		public LocalBest() {
			localBestValue = Double.MAX_VALUE;
		}

		public double getLocalBestValue() {
			return localBestValue;
		}

		public void setLocalBestValue(double localBestValue) {
			this.localBestValue = localBestValue;
		}

		public double[] getLocalBest() {
			return localBest;
		}

		public void setLocalBest(double[] localBest) {
			this.localBest = localBest;
		}

	}

}
