package hr.fer.zemris.optjava.dz7.neural_network;

public class SigmoidTransferFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return 1.0 / (1 + Math.pow(Math.E, -x));
	}

}
