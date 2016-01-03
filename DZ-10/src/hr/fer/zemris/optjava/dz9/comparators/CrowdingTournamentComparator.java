package hr.fer.zemris.optjava.dz9.comparators;

import java.util.Comparator;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

/**
 * This comparator is used in crowding tournament selection. Chromosome wins the
 * tournament if it has better solution rank or equal solution rank as other
 * chromosome but bigger crowding distance.
 */
public class CrowdingTournamentComparator implements Comparator<Chromosome> {

	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		if (o1.n < o2.n) {
			return -1;
		} else if (o1.n > o2.n) {
			return 1;
		} else if (o1.getCrowdingDistance() > o2.getCrowdingDistance()) {
			return -1;
		} else if (o1.getCrowdingDistance() < o2.getCrowdingDistance()) {
			return 1;
		}
		return 0;
	}

}
