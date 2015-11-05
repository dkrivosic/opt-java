package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * One chromosome which represents assignment of factories to places.
 */
public class Chromosome implements Comparable<Chromosome> {

	public int[] p;
	private double cost;
	private int n;

	public Chromosome(int n) {
		this.p = new int[n];
		this.n = n;
		for (int i = 0; i < n; i++) {
			p[i] = -1;
		}
	}

	public void randomize() {
		List<Integer> ls = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ls.add(i);
		}
		Collections.shuffle(ls);
		for (int i = 0; i < n; i++) {
			p[i] = ls.get(i);
		}
	}

	public void evaluate(CostFunction function) {
		this.cost = function.costOf(this);
	}

	public double getCost() {
		return cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Chromosome)) {
			return false;
		}

		Chromosome c = (Chromosome) obj;
		for (int i = 0; i < n; i++) {
			if (this.p[i] != c.p[i]) {
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
			hc += i * p[i] * prime;
		}
		return hc;
	}

	@Override
	public String toString() {
		String str = "( ";
		for (int i = 0; i < n; i++) {
			str += p[i] + " ";
		}
		return str + ")";
	}

	@Override
	public int compareTo(Chromosome o) {
		if (this.getCost() < o.getCost()) {
			return -1;
		} else if (this.getCost() > o.getCost()) {
			return 1;
		}
		return 0;
	}
}
