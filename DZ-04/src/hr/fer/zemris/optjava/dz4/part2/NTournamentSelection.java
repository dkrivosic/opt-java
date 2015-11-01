package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NTournamentSelection {
	private int n;
	private Random random;

	public NTournamentSelection(int n) {
		this.n = n;
		this.random = new Random();
	}

	/**
	 * Performs n-tournament selection. If <code>best</code> flag is set to
	 * true, it returns the best chromosome from the pool, otherwise return the
	 * worst.
	 */
	public Chromosome select(List<Chromosome> population, boolean best) {
		List<Chromosome> pool = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int index = random.nextInt(population.size());
			pool.add(population.get(index));
		}
		Collections.sort(pool);
		if (best) {
			return pool.get(n - 1);
		} else {
			return pool.get(0);
		}
	}

}
