package hr.fer.zemris.optjava.dz4.part1;

import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
	public double[] values;
	public double fitness;
	public double error;

	public Chromosome(int n) {
		values = new double[n];
	}

	public void randomize(double min, double max) {
		Random rand = new Random();
		for (int i = 0; i < values.length; i++) {
			values[i] = rand.nextDouble() * (max - min) + min;
		}
	}

	@Override
	public int compareTo(Chromosome o) {
		if (this.fitness < o.fitness) {
			return -1;
		} else if (this.fitness > o.fitness) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		String str = "[ ";
		int n = values.length;
		for (int i = 0; i < n; i++) {
			str += values[i] + " ";
		}
		return str + "]";
	}
}
