package hr.fer.zemris.optjava.dz9.functions;

/**
 * Function returns i-th component of a solution.
 */
public class Identity implements IFunction {
	private int i;

	public Identity(int i) {
		this.i = i;
	}

	@Override
	public double valueAt(double[] solution) {
		return solution[i];
	}

}
