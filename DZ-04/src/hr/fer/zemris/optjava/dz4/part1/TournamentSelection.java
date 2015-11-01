package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements ISelection {
	private Random random;
	private int n;
	private boolean best;

	public TournamentSelection(Random random, int n, boolean best) {
		this.random = random;
		this.n = n;
		this.best = best;
	}

	@Override
	public Chromosome select(Population population) {
		int size = population.size();
		List<Chromosome> pool = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			int index = random.nextInt(size);
			pool.add(population.getAll().get(index));
		}
		Collections.sort(pool);
		if (best) {
			return pool.get(n - 1);
		} else {
			return pool.get(0);
		}
	}

}
