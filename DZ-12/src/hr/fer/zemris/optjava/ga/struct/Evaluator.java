package hr.fer.zemris.optjava.ga.struct;

import java.util.Arrays;

import hr.fer.zemris.optjava.bool.BooleanExpressionSolver;

/**
 * Evaluator.
 */
public class Evaluator {
	private int numberOfVariables;
	private BooleanExpressionSolver solver;

	public Evaluator(BooleanExpressionSolver solver) {
		super();
		this.numberOfVariables = solver.getNumberOfVariables();
		this.solver = solver;
	}

	/**
	 * Evaluates Chromosome c and sets its error and bestCLB fields. Error for
	 * each CLB is calculated as number of inputs with different outputs. CLB
	 * with the lowest error is set as best CLB and its error becomes
	 * Chromosome's error.
	 * 
	 * @param c
	 *            Chromosome to evaluate
	 */
	public void evaluate(Chromosome c) {
		int[][] combinations = BooleanExpressionSolver.binaryGenerator(numberOfVariables);
		int n = combinations.length;

		Integer[] expectedValues = solver.getAllValues();
		int[] errors = new int[c.getNumberOfCLBs()];
		Arrays.fill(errors, 0);

		for (int i = 0; i < n; i++) {
			int[] output = c.calculateOutputs(combinations[i]);
			for (int j = 0; j < output.length; j++) {
				if (output[j] != expectedValues[i]) {
					errors[j]++;
				}
			}
		}

		int minError = Integer.MAX_VALUE;
		int minErrIndex = -1;
		for (int i = 0; i < errors.length; i++) {
			if (errors[i] < minError) {
				minError = errors[i];
				minErrIndex = i;
			}
		}

		c.error = minError;
		c.bestCLB = minErrIndex;
	}

}
