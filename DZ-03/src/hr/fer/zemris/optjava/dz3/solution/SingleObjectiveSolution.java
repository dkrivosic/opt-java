package hr.fer.zemris.optjava.dz3.solution;

public abstract class SingleObjectiveSolution {

	private double fitness;
	private double value;

	/**
	 * Compares current solution to given solution.
	 */
	public double compareTo(SingleObjectiveSolution solution) {
		return solution.fitness - this.fitness;
	}

	public void setValue(double value, boolean minimize) {
		this.value = value;
		if (minimize) {
			fitness = -value;
		} else {
			fitness = value;
		}
	}

	public double getValue() {
		return value;
	}

	public double getFitness() {
		return fitness;
	}

	/**
	 * Creates new solution of same length as current.
	 */
	public abstract SingleObjectiveSolution newLikeThis();

	/**
	 * Creates copy of current solution.
	 */
	public abstract SingleObjectiveSolution duplicate();

}
