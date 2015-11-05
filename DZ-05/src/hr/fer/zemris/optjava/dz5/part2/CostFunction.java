package hr.fer.zemris.optjava.dz5.part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CostFunction {

	/**
	 * Matrix of distances between locations.
	 */
	private int[][] d;
	/**
	 * Matrix of amount of product transfered between factories.
	 */
	private int[][] c;

	private int n;

	private CostFunction(int[][] d, int[][] c) {
		this.c = c;
		this.d = d;
		this.n = c[0].length;
	}

	public double costOf(Chromosome chr) {
		double cost = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				cost += c[i][j] * d[chr.p[i]][chr.p[j]];
			}
		}
		return cost;
	}

	public int getN() {
		return n;
	}

	public static CostFunction readFromFile(String path) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(path));
		int n = scanner.nextInt();
		int[][] d = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				d[i][j] = scanner.nextInt();
			}
		}

		int[][] c = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				c[i][j] = scanner.nextInt();
			}
		}
		scanner.close();

		return new CostFunction(d, c);
	}
}
