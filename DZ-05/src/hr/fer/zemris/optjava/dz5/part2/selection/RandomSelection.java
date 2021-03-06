package hr.fer.zemris.optjava.dz5.part2.selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.Chromosome;

/**
 * Randomly selects chromosome.
 */
public class RandomSelection implements ISelection {

	private Random random;

	public RandomSelection(Random random) {
		this.random = random;
	}

	@Override
	public Chromosome select(Set<Chromosome> population) {
		List<Chromosome> ls = new ArrayList<>(population);
		int i = random.nextInt(ls.size());
		return ls.get(i);
	}

}
