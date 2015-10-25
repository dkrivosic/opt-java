package hr.fer.zemris.optjava.dz3.algorithm;

public class GeometricTempSchedule implements ITempSchedule {
	private double alpha;
	private double tInitial;
	private double tCurrent;
	private int innerLimit;
	private int outerLimit;
	private int innerCounter;
	private int outerCounter;

	public GeometricTempSchedule(double alpha, double tInitial, int innerLimit) {
		this.alpha = alpha;
		this.tInitial = tInitial;
		this.tCurrent = this.tInitial;
		this.innerLimit = innerLimit;
		this.innerCounter = 0;
		this.outerCounter = 0;
		this.tCurrent = tInitial;

		this.outerLimit = (int) Math.round(1 / Math.log(alpha) * Math.log(1 / tInitial));
	}

	/**
	 * Updates counters and returns current temperature.
	 */
	@Override
	public double getNextTemperature() {
		innerCounter++;
		if (innerCounter == innerLimit) {
			innerCounter = 0;
			outerCounter++;
			tCurrent *= alpha;
		}
		if (outerCounter == outerLimit) {
			return -1;
		}

		return tCurrent;
	}

	@Override
	public int getInnerLoopCounter() {
		return innerCounter;
	}

	@Override
	public int getOuterLoopCounter() {
		return outerCounter;
	}

}
