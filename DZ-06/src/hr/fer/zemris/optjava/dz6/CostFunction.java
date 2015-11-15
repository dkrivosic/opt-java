package hr.fer.zemris.optjava.dz6;

import java.util.List;

public class CostFunction {

	private double[][] distance;

	public CostFunction(double[][] distance) {
		this.distance = distance;
	}

	/**
	 * Calculates the total distance of a cycle.
	 */
	public double valueOf(List<Integer> route) {
		double cost = 0;
		int n = route.size();
		for (int i = 0; i < n - 1; i++) {
			cost += distance[route.get(i)][route.get(i + 1)];
		}
		cost += distance[route.get(0)][route.get(n - 1)];

		return cost;
	}
}
