package hr.fer.zemris.optjava.dz3.algorithm;

/**
 * Temperature change schedule for simulated annealing algorithm.
 */
public interface ITempSchedule {

	public double getNextTemperature();

	public int getInnerLoopCounter();

	public int getOuterLoopCounter();
}
