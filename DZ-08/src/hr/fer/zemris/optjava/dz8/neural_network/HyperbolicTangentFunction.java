package hr.fer.zemris.optjava.dz8.neural_network;

public class HyperbolicTangentFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 2 * sigmoid(x) - 1;
	}
	
	private double sigmoid(double x) {
		return 1.0 / (1 + Math.pow(Math.E, -x));
	}

}
