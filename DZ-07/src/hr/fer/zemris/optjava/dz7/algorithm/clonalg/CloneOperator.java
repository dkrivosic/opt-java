package hr.fer.zemris.optjava.dz7.algorithm.clonalg;

import java.util.ArrayList;
import java.util.List;

/**
 * Cloning operator.
 */
public class CloneOperator {

	/**
	 * Creates population of clones from given population.
	 * 
	 * @param population
	 *            population
	 * @param beta
	 *            Parameter used for determining clone population size
	 * @return population of clones.
	 */
	public List<Antibody> clone(List<Antibody> population, double beta) {
		int n = population.size();
		List<Antibody> clones = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int nc = (int) Math.round(beta * n / (i + 1));
			for (int j = 0; j < nc; j++) {
				clones.add(population.get(i).clone());
			}
		}
		return clones;
	}
}
