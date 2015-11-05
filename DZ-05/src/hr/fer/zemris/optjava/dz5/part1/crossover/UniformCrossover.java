package hr.fer.zemris.optjava.dz5.part1.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part1.Chromosome;

/**
 * Uniform crossover.
 */
public class UniformCrossover implements ICrossoverOperation {
	private Random random;

	public UniformCrossover(Random random) {
		this.random = random;
	}

	@Override
	public List<Chromosome> crossover(Chromosome firstParent, Chromosome secondParent) {
		int n = firstParent.n;
		Chromosome firstChild = new Chromosome(n);
		Chromosome secondChild = new Chromosome(n);
		Chromosome r = new Chromosome(n);
		r.randomize(random);

		int[] v1 = firstParent.v;
		int[] v2 = secondParent.v;
		int[] v = r.v;

		firstChild.v = or(and(v1, v2), and(v, xor(v1, v2)));
		secondChild.v = or(and(v1, v2), and(not(v), xor(v1, v2)));

		List<Chromosome> children = new ArrayList<>();
		children.add(firstChild);
		children.add(secondChild);

		return children;
	}

	/**
	 * Logical and operation.
	 */
	private int[] and(int[] a, int[] b) {
		int[] c = new int[a.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = a[i] & b[i];
		}
		return c;
	}

	/**
	 * Logical or operation.
	 */
	private int[] or(int[] a, int[] b) {
		int[] c = new int[a.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = a[i] | b[i];
		}
		return c;
	}

	/**
	 * Logical xor operation.
	 */
	private int[] xor(int[] a, int[] b) {
		int[] c = new int[a.length];
		for (int i = 0; i < c.length; i++) {
			c[i] = a[i] ^ b[i];
		}
		return c;
	}

	/**
	 * Logical not operation.
	 */
	private int[] not(int[] a) {
		int[] b = new int[a.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = 1 - a[i];
		}
		return b;
	}

}
