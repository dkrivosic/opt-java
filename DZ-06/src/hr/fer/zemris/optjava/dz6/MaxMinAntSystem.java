package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Phaser;

public class MaxMinAntSystem {
	private int antsNum;
	private int maxIter;
	private double alpha;
	private CostFunction function;
	private double[][] heuristicInfo;
	private List<List<Integer>> candidatesList;
	private double rho;
	private double tmax;
	private double tmin;
	private int n;
	private Random random;
	private double[][] pheromones;

	public MaxMinAntSystem(int antsNum, int maxIter, double alpha, CostFunction function,
			double[][] heuristicInfo, List<List<Integer>> candidatesList, double rho, double tmax, double a) {
		this.antsNum = antsNum;
		this.maxIter = maxIter;
		this.alpha = alpha;
		this.function = function;
		this.heuristicInfo = heuristicInfo;
		this.candidatesList = candidatesList;
		this.rho = rho;
		this.tmax = tmax;
		this.n = heuristicInfo.length;
		this.random = new Random();
		this.tmin = tmax / a;
	}

	/**
	 * Starts the algorithm and returns the best ant.
	 */
	public Ant run() {
		List<Ant> ants;
		ants = newAnts();

		pheromones = resetPheromones(n, tmax);

		int iter = 0;
		int bestIter = 0;
		final int stagnationIter = (int) (maxIter * 0.1);
		Ant bestSoFar = ants.get(0);
		while (iter < maxIter) {
			iter++;

			Ant iterBest = null;
			for (int j = 0; j < antsNum; j++) {
				generateSolution(ants.get(j));
				ants.get(j).evaluate(function);
				if (iterBest == null || ants.get(j).getCost() < iterBest.getCost()) {
					iterBest = ants.get(j);
				}
			}

			if (bestSoFar == null || iterBest.getCost() < bestSoFar.getCost()) {
				bestSoFar = iterBest;
				bestIter = 0;
			}
			
			// If stagnation occures reset pheromones
			if(bestIter >= stagnationIter) {
				bestIter = 0;
				pheromones = resetPheromones(n, tmax);
			}
			bestIter++;

			double p = random.nextDouble();
			if (p < iter / maxIter) {
				updatePheromones(bestSoFar);
			} else {
				updatePheromones(iterBest);
			}

			evaporatePheromones();
			ants = newAnts();
			
			System.out.println("iteration: " + iter + "/" + maxIter + ", best so far: " + bestSoFar.getCost());
		}

		return bestSoFar;
	}

	/**
	 * Create Ant generation and add random starting city.
	 */
	public List<Ant> newAnts() {
		List<Ant> ants = new ArrayList<>();
		for (int i = 0; i < antsNum; i++) {
			Ant ant = new Ant();
			ant.visit(random.nextInt(n));
			ants.add(ant);
		}
		return ants;
	}

	/**
	 * Pheromones evaporate on all roads. Pheromone concentration cannot be less
	 * than <code>tmin</code>.
	 */
	private void evaporatePheromones() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (pheromones[i][j] * (1 - rho) > tmin) {
					pheromones[i][j] = pheromones[i][j] * (1 - rho);
				} else {
					pheromones[i][j] = tmin;
				}
			}
		}
	}

	/**
	 * Chosen ant deposits feromones on its route. Pheromone concentration
	 * cannot be greater than <code>tmax</code>.
	 */
	private void updatePheromones(Ant ant) {
		List<Integer> route = ant.getRoute();
		for (int i = 0; i < n; i++) {
			int j = (i + 1) % n;
			int first = route.get(i);
			int second = route.get(j);
			if (pheromones[first][second] + 1.0 / ant.getCost() < tmax) {
				pheromones[first][second] += 1.0 / ant.getCost();
				pheromones[first][second] += 1.0 / ant.getCost();
			} else {
				pheromones[i][j] = tmax;
				pheromones[j][i] = tmax;
			}
		}
	}

	/**
	 * Generates route for one ant.
	 */
	private void generateSolution(Ant ant) {
		int currentCity = ant.getStartingCity();
		for (int i = 0; i < n - 1; i++) {
			List<Integer> currentCandidates = candidatesList.get(currentCity);
			if (ant.visitedAll(currentCandidates)) {
				int next;
				do {
					next = random.nextInt(n);
				} while (ant.visited(next));
				currentCity = next;
				ant.visit(currentCity);
			} else {
				currentCity = chooseNextCity(currentCity, ant);
				ant.visit(currentCity);
			}

		}
	}

	/**
	 * Chooses next city to visit from candidates list.
	 */
	private int chooseNextCity(int currentCity, Ant ant) {
		List<Integer> currentCandidates = candidatesList.get(currentCity);
		double sum = 0;
		for (int c : currentCandidates) {
			sum += heuristicInfo[currentCity][c] * Math.pow(pheromones[currentCity][c], alpha);
		}
		int next = -1;
		double p = random.nextDouble();
		double pSum = 0;
		for (int c : currentCandidates) {
			pSum += heuristicInfo[currentCity][c] * Math.pow(pheromones[currentCity][c], alpha) / sum;
			if (pSum >= p && !ant.visited(c)) {
				next = c;
				break;
			}
		}

		if (next < 0) {
			for (int c : currentCandidates) {
				if (!ant.visited(c)) {
					next = c;
				}
			}
		}

		return next;
	}

	/**
	 * Set pheromones concentrations on all roads to tmax.
	 */
	private double[][] resetPheromones(int n, double t0) {
		double[][] pheromones = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				pheromones[i][j] = t0;
			}
		}
		return pheromones;
	}

}
