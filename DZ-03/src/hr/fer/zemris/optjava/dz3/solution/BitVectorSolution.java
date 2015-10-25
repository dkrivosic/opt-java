package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

/**
 * Solution represented by vector of bits.
 */
public class BitVectorSolution extends SingleObjectiveSolution {

	public byte[] bits;

	public BitVectorSolution(int n) {
		bits = new byte[n];
	}

	@Override
	public BitVectorSolution newLikeThis() {
		return new BitVectorSolution(bits.length);
	}

	@Override
	public BitVectorSolution duplicate() {
		int n = bits.length;
		BitVectorSolution solution = new BitVectorSolution(n);
		for (int i = 0; i < n; i++) {
			solution.bits[i] = this.bits[i];
		}
		return solution;
	}

	public void randomize(Random random) {
		int n = bits.length;
		for (int i = 0; i < n; i++) {
			bits[i] = (byte) (random.nextBoolean() ? 1 : 0);
		}
	}
	
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < bits.length; i++) {
			str += bits[i];
		}
		return str;
	}
}
