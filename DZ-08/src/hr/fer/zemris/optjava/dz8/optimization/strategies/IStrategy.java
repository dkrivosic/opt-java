package hr.fer.zemris.optjava.dz8.optimization.strategies;

import java.util.List;

import hr.fer.zemris.optjava.dz8.optimization.Vector;

public interface IStrategy {

	/**
	 * Generates trial vector.
	 */
	public Vector generateTrial(List<Vector> population, int targetIndex);
}
