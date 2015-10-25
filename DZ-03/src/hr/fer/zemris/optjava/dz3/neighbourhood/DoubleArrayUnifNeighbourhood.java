package hr.fer.zemris.optjava.dz3.neighbourhood;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

/**
 * Random neighbour generator (Uniform distribution).
 */
public class DoubleArrayUnifNeighbourhood implements INeighbourhood<DoubleArraySolution> {

	private double[] deltas;
	Random rand;

	public DoubleArrayUnifNeighbourhood(double[] deltas) {
		this.deltas = deltas;
		this.rand = new Random();
	}

	@Override
	public DoubleArraySolution randomNeighbour(DoubleArraySolution solution) {
		DoubleArraySolution neighbour = solution.duplicate();
		for (int i = 0; i < neighbour.n; i++) {
			neighbour.values[i] += rand.nextDouble() * 2 * deltas[i] - deltas[i];
		}
		return neighbour;
	}

}
