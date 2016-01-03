package hr.fer.zemris.optjava.dz9.comparators;

import java.util.Comparator;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

/**
 * Compares two chromosomes by their crowding distances.
 */
public class CrowdingSortComparator implements Comparator<Chromosome> {

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		if (o1.getCrowdingDistance() < o2.getCrowdingDistance()) {
			return -1;
		} else if (o1.getCrowdingDistance() > o2.getCrowdingDistance()) {
			return 1;
		}
		return 0;
	}

}
