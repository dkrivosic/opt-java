package hr.fer.zemris.optjava.dz8.neural_network;

public class IdentityFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return x;
	}

}
