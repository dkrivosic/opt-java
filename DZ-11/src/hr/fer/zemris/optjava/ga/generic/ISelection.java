package hr.fer.zemris.optjava.ga.generic;

import java.util.List;

/**
 * Selection operator. 
 */
public interface ISelection<T> {
	
	/**
	 * Selects one solution from population.
	 * 
	 * @param population Population of solutions.
	 * @return selected solution.
	 */
	public GASolution<T> select(List<GASolution<T>> population);
}
