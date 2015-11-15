package hr.fer.zemris.optjava.dz6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Traveling salesman problem solver.
 */
public class TSPSolver {

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 4) {
			System.err.println("4 arguments expected: \n"
					+ "file with problem description, candidate list size, number of ants, max iterations.");
			System.exit(0);
		}

		int k = Integer.parseInt(args[1]);
		int antsNum = Integer.parseInt(args[2]);
		int maxIter = Integer.parseInt(args[3]);

		double alpha = 0.9;
		double beta = 2;
		double rho = 0.02;

		double[][] distance = readFile(args[0]);
		int n = distance.length;
		double[][] heuristicInfo = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				heuristicInfo[i][j] = Math.pow(1.0 / distance[i][j], beta);
			}
		}

		CostFunction function = new CostFunction(distance);
		List<List<Integer>> candidatesList = generateCandidatesList(distance, k);

		double tmax = 1.0 / (rho * greedyAlgorithm(distance, function));
		double a = 100; // TODO

		MaxMinAntSystem algorithm = new MaxMinAntSystem(antsNum, maxIter, alpha, function, heuristicInfo,
				candidatesList, rho, tmax, a);

		Ant bestAnt = algorithm.run();

		System.out.println(bestAnt);
		System.out.println("cost = " + bestAnt.getCost());

	}

	/**
	 * For each city generates list of candidate cities to visit next.
	 * Candidates are <code>k</code> closest cities.
	 */
	private static List<List<Integer>> generateCandidatesList(double[][] distance, int k) {
		List<List<Integer>> candidates = new ArrayList<>();
		int n = distance.length;
		for (int i = 0; i < n; i++) {
			List<Pair<Integer, Double>> tmp = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				tmp.add(new Pair<>(j, distance[i][j]));
			}
			Collections.sort(tmp);
			List<Integer> currentCand = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				if (currentCand.size() < k && tmp.get(j).getFirst() != candidates.size()) {
					currentCand.add(tmp.get(j).getFirst());
				} else if (j == k) {
					break;
				}
			}
			candidates.add(currentCand);
		}

		return candidates;
	}

	/**
	 * Reads coordinates of cities from file and returns distance matrix.
	 * 
	 * @throws FileNotFoundException
	 *             if invalid path is given
	 */
	private static double[][] readFile(String path) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(path));
		String line = scanner.nextLine();
		while (line.matches(".*[a-zA-Z]+[a-zA-Z].*")) {
			line = scanner.nextLine();
		}

		List<Pair<Double, Double>> cities = new ArrayList<>();
		while (!line.contains("EOF")) {
			String[] tmp = line.split("[ ]+");
			double x = Double.parseDouble(tmp[1]);
			double y = Double.parseDouble(tmp[2]);
			cities.add(new Pair<Double, Double>(x, y));
			line = scanner.nextLine();
		}
		scanner.close();

		int n = cities.size();
		double dist[][] = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Pair<Double, Double> c1 = cities.get(i);
				Pair<Double, Double> c2 = cities.get(j);
				dist[i][j] = Math.sqrt(
						Math.pow(c1.getFirst() - c2.getFirst(), 2) + Math.pow(c1.getSecond() - c2.getSecond(), 2));
			}
		}

		return dist;
	}

	/**
	 * Greedy algorithm for finding the shortest path. It is used to calculate
	 * <code>tmax</code>.
	 */
	private static double greedyAlgorithm(double[][] distance, CostFunction function) {
		Ant ant = new Ant();
		ant.visit(0);
		int n = distance.length;
		int current = 0;
		for (int i = 0; i < n - 1; i++) {
			int minIndex = 0;
			double minDist = Double.MAX_VALUE;
			for (int j = 0; j < n; j++) {
				if (distance[current][j] < minDist && !ant.visited(j)) {
					minDist = distance[current][j];
					minIndex = j;
				}
			}
			current = minIndex;
			ant.visit(minIndex);

		}
		ant.evaluate(function);

		return ant.getCost();
	}

	/**
	 * A pair of values. Pairs are compared by second value.
	 */
	static class Pair<R, T extends Comparable<T>> implements Comparable<Pair<R, T>> {
		private R first;
		private T second;

		public Pair(R first, T second) {
			this.first = first;
			this.second = second;
		}

		public R getFirst() {
			return first;
		}

		public T getSecond() {
			return second;
		}

		@Override
		public int compareTo(Pair<R, T> o) {
			return second.compareTo(o.getSecond());
		}

	}
}
