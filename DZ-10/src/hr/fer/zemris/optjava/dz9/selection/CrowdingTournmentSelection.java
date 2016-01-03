package hr.fer.zemris.optjava.dz9.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;
import hr.fer.zemris.optjava.dz9.comparators.CrowdingTournamentComparator;

/**
 * Crowding Tournament Selection.
 */
public class CrowdingTournmentSelection implements ISelection {
	private int tournamentSize;
	private Random random;

	public CrowdingTournmentSelection(int tournamentSize) {
		this.tournamentSize = tournamentSize;
		this.random = new Random();
	}

	@Override
	public Chromosome select(List<Chromosome> population) {
		List<Chromosome> pool = new ArrayList<Chromosome>();
		for (int i = 0; i < tournamentSize; i++) {
			int index = random.nextInt(population.size());
			pool.add(population.get(index));
		}

		Collections.sort(pool, new CrowdingTournamentComparator());

		return pool.get(0);
	}

}
