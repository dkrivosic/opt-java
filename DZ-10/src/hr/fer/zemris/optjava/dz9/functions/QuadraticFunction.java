package hr.fer.zemris.optjava.dz9.functions;

/**
 * Function's value is equal to solution's i-th component squared.
 */
public class QuadraticFunction implements IFunction {
	private int i;

	public QuadraticFunction(int i) {
		this.i = i;
	}

	@Override
	public double valueAt(double[] solution) {
		return Math.pow(solution[i], 2);
	}

}
