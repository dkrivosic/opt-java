package hr.fer.zemris.optjava.dz9.moop;

/**
 * Multi objective optimization problem.
 */
public interface MOOPProblem {

	/**
	 * @return number of objective functions.
	 */
	public int getNumberOfObjectives();

	/**
	 * Evaluates solution and stores result in the <code>objective</code> array.
	 */
	public void evaluateSolution(double[] solution, double[] objective);

	/**
	 * Evaluates solution and returns result.
	 */
	public double[] evaluateSolution(double[] solution);

	/**
	 * @return solution dimension.
	 */
	public int getSolutionSize();
}
