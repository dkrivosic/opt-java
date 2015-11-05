package hr.fer.zemris.optjava.dz5.part1.comparison;

/**
 * Always returns comparison factor set in the constructor.
 */
public class ConstantCompFactor implements ICompFactorPlan {
	private double compFactor;

	public ConstantCompFactor(double compFactor) {
		this.compFactor = compFactor;
	}

	@Override
	public double getCompFactor() {
		return compFactor;
	}

}
