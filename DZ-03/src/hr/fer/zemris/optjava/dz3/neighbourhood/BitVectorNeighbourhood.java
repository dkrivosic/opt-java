package hr.fer.zemris.optjava.dz3.neighbourhood;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Random neighbour generator for solution represented as vector of bits.
 */
public class BitVectorNeighbourhood implements INeighbourhood<SingleObjectiveSolution> {

	@Override
	public BitVectorSolution randomNeighbour(SingleObjectiveSolution solution) {
		BitVectorSolution s = (BitVectorSolution) solution;
		int n = s.bits.length;
		Random random = new Random();
		int index = random.nextInt(n);
		BitVectorSolution neighbour = s.duplicate();
		neighbour.bits[index] = (byte) (neighbour.bits[index] == 1 ? 0 : 1);
		return neighbour;
	}

}
