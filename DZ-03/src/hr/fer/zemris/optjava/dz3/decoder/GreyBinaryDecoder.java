package hr.fer.zemris.optjava.dz3.decoder;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Decodes bit vector coded with grey code to array of <code>double</code>
 * values.
 */
public class GreyBinaryDecoder extends BitVectorDecoder {
	private NaturalBinaryDecoder binaryDecoder;

	public GreyBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
		this.binaryDecoder = new NaturalBinaryDecoder(mins, maxs, bits, n);
	}

	public GreyBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);
		this.binaryDecoder = new NaturalBinaryDecoder(min, max, bits, n);
	}

	@Override
	public double[] decode(SingleObjectiveSolution solution) {
		BitVectorSolution s = (BitVectorSolution) solution;
		BitVectorSolution bSolution = s.newLikeThis();
		int start = 0;
		for (int i = 0; i < n; i++) {
			byte[] binary = greyToBinary(Arrays.copyOfRange(s.bits, start, start + bits[i]));
			for (int j = 0; j < binary.length; j++) {
				bSolution.bits[start + j] = binary[j];
			}
			start += bits[i];
		}
		return binaryDecoder.decode(bSolution);
	}

	@Override
	public void decode(SingleObjectiveSolution solution, double[] v) {
		double[] tmp = decode(solution);
		for (int i = 0; i < tmp.length; i++) {
			v[i] = tmp[i];
		}

	}

	private byte[] greyToBinary(byte[] grey) {
		int n = grey.length;
		byte[] binary = new byte[n];
		binary[0] = grey[0];
		for (int i = 2; i < n; i++) {
			binary[i] = (byte) (binary[i - 1] ^ grey[i]);
		}
		return binary;
	}

}
