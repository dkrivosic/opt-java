package hr.fer.zemris.optjava.ga;

/**
 * optimization algorithm.
 */
public abstract class IOptAlgorithm {
	public volatile boolean finished;

	/**
	 * Runs the algorithm.
	 */
	public abstract void run();
	
	
	
}
