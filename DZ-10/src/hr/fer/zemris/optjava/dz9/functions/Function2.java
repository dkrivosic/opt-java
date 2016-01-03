package hr.fer.zemris.optjava.dz9.functions;

/**
 * f(x) = (1 + x2) / x1 
 */
public class Function2 implements IFunction {

	@Override
	public double valueAt(double[] solution) {
		return (1 + solution[0]) / solution[1];
	}

}
