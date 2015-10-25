package hr.fer.zemris.optjava.dz3.neighbourhood;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Random neighbour generator (Gaussian/Normal distribution).
 */
public class DoubleArrayNormNeighbourhood implements INeighbourhood<SingleObjectiveSolution> {

	private double[] deltas;
	Random rand;

	public DoubleArrayNormNeighbourhood(double[] deltas) {
		this.deltas = deltas;
		this.rand = new Random();
	}

	@Override
	public DoubleArraySolution randomNeighbour(SingleObjectiveSolution solution) {
		DoubleArraySolution s = (DoubleArraySolution) solution;
		DoubleArraySolution neighbour = s.duplicate();
		for (int i = 0; i < neighbour.n; i++) {
			neighbour.values[i] += rand.nextGaussian() * deltas[i];
		}
		return neighbour;
	}

}
