package hr.fer.zemris.optjava.dz9.functions;

/**
 * Hard constraint for optimization problem.
 */
public interface IHardConstraint {

	/**
	 * @return true if solution satisfies constraint, false otherwise.
	 */
	public boolean satisfied(double[] solution);
	
	/**
	 * Corrects solution so it satisfies constraints. 
	 */
	public void correctSolution(double[] solution);
}
