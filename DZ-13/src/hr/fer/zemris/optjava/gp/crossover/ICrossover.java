package hr.fer.zemris.optjava.gp.crossover;

import java.util.List;

import hr.fer.zemris.optjava.gp.ant.Ant;

/**
 * Crossover operator.
 */
public interface ICrossover {

	/**
	 * Performs crossover operation on two parent ants and returns list of
	 * children.
	 * 
	 * @param firstParent
	 *            first parent
	 * @param secondParent
	 *            second parent
	 * @return children
	 */
	public List<Ant> crossover(Ant firstParent, Ant secondParent);
}
