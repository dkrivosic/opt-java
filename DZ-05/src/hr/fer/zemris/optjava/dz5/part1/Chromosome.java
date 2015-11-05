package hr.fer.zemris.optjava.dz5.part1;

import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
	public int[] v;
	public final int n;
	public double fitness;

	public Chromosome(int n) {
		this.v = new int[n];
		this.n = n;
	}

	public void randomize(Random random) {
		for (int i = 0; i < n; i++) {
			v[i] = random.nextInt(2);
		}
	}

	public Chromosome copy() {
		Chromosome c = new Chromosome(n);
		for (int i = 0; i < n; i++) {
			c.v[i] = v[i];
		}
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Chromosome)) {
			return false;
		}

		Chromosome c = (Chromosome) obj;
		for (int i = 0; i < n; i++) {
			if (this.v[i] != c.v[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 7;
		int hc = 0;
		for (int i = 0; i < n; i++) {
			hc += i * v[i] * prime;
		}
		return hc;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < n; i++) {
			str += v[i];
		}
		return str;
	}

	@Override
	public int compareTo(Chromosome o) {
		if (o.fitness > this.fitness) {
			return 1;
		} else if (o.fitness < this.fitness) {
			return -1;
		}
		return 0;
	}
}
