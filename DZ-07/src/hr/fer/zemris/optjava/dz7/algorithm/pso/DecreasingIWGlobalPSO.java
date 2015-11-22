package hr.fer.zemris.optjava.dz7.algorithm.pso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.neural_network.INeuralNetwork;

/**
 * Decreasing inertia weight particle swarm optimization algorithm with ring
 * topology.
 */
public class DecreasingIWGlobalPSO implements IOptAlgorithm {
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

	public DecreasingIWGlobalPSO(int swarmSize, int maxIterations, int dimension, double[] xmin, double[] xmax,
			double[] vmin, double[] vmax, INeuralNetwork neuralNetwork, double merr, double cIndividual, double cSocial,
			double wmin, double wmax) {
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
	}

	@Override
	public double[] run() {
		Random random = new Random();
		InertiaWeight w = new InertiaWeight(wmin, wmax, maxIterations);

		List<Particle> swarm = new ArrayList<>();
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
			swarm.add(p);
		}

		double globalBestValue = Double.MAX_VALUE;
		double[] globalBest = null;

		int iter = 0;
		int dots = maxIterations / 100;
		do {
			iter++;
			if (iter % dots == 0)
				System.out.print(".");
			// Evaluate swarm and update personal and global best
			for (Particle p : swarm) {
				double xerr = neuralNetwork.getError(p.getX());
				if (xerr < p.getPersonalBestValue()) {
					p.setPersonalBest(Arrays.copyOf(p.getX(), p.getX().length));
					p.setPersonalBestValue(xerr);
				}
				if (xerr < globalBestValue) {
					globalBestValue = xerr;
					globalBest = Arrays.copyOf(p.getX(), p.getX().length);
				}
			}

			// Update particle speed and position
			for (Particle p : swarm) {
				double v[] = p.getV();
				double x[] = p.getX();
				double pBest[] = p.getPersonalBest();
				for (int d = 0; d < dimension; d++) {
					v[d] = w.getWeight(iter) * v[d] + cIndividual * random.nextDouble() * (pBest[d] - x[d])
							+ cSocial * random.nextDouble() * (globalBest[d] - x[d]);
					v[d] = checkBorders(v[d], vmin[d], vmax[d]);
					x[d] = x[d] + v[d];
					x[d] = checkBorders(x[d], xmin[d], xmax[d]);
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

}
