package hr.fer.zemris.optjava.dz9.comparators;

import java.util.Comparator;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

/**
 * Compares two Chromosomes by i-th objective function.
 */
public class ObjectiveFunctionComparator implements Comparator<Chromosome> {
	private int i;

	public ObjectiveFunctionComparator(int i) {
		this.i = i;
	}

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		if (o1.objective[i] < o2.objective[i]) {
			return -1;
		} else if (o1.objective[i] > o2.objective[i]) {
			return 1;
		}
		return 0;
	}

}
