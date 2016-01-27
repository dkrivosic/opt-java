package hr.fer.zemris.optjava.ga.generic;

/**
 * Crossover operator. 
 */
public interface ICrossover<T> {

	/**
	 * Takes two parents and generates a child solution.
	 * 
	 * @return child.
	 */
	public GASolution<T> crossover(GASolution<T> firstParent, GASolution<T> secondParent);
}
