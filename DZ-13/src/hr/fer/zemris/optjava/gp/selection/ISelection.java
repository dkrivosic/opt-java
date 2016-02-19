package hr.fer.zemris.optjava.gp.selection;

import java.util.List;

import hr.fer.zemris.optjava.gp.ant.Ant;

/**
 * Selection.
 */
public interface ISelection {

	/**
	 * Selects one Ant from population of ants and returns its copy.
	 */
	public Ant select(List<Ant> population);
}
