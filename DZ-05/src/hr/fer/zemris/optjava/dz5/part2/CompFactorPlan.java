package hr.fer.zemris.optjava.dz5.part2;

/**
 * Plan for changing comparison factor.
 */
public class CompFactorPlan {
	private double compFactor;
	private double upperBound;
	private double lowerBound;
	private double delta;

	public CompFactorPlan(double lowerBound, double upperBound, double delta) {
		this.compFactor = lowerBound;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.delta = delta;
	}

	/**
	 * Each time this method is called, comparison factor is increased by
	 * <code>delta</code>.
	 */
	public double getCompFactor() {
		if (compFactor + delta > upperBound) {
			return upperBound;
		}

		compFactor += delta;
		return compFactor;
	}

	/**
	 * Sets the comparison factor to lower bound.
	 */
	public void reset() {
		compFactor = lowerBound - delta;
	}

}
