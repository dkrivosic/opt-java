package hr.fer.zemris.optjava.gp.selection;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class TournamentSelection implements ISelection {
	private int n;
	private Random random;

	public TournamentSelection(int n, Random random) {
		super();
		this.n = n;
		this.random = random;
	}

	@Override
	public Ant select(List<Ant> population) {
		Set<Ant> tournament = new HashSet<>();
		int popSize = population.size();

		while (tournament.size() < n) {
			int i = random.nextInt(popSize);
			tournament.add(population.get(i));
		}

		Ant best = tournament.iterator().next();
		for (Ant a : tournament)
			if (a.getFitness() > best.getFitness()) {
				best = a;
			}

		Ant selected = best.copy();
		selected.setParentFoodEaten(best.getParentFoodEaten());
		return selected;
	}

}
