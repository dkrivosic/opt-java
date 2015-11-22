package hr.fer.zemris.optjava.dz7.decoder;

import hr.fer.zemris.optjava.dz7.algorithm.clonalg.Antibody;

/**
 * Used for decoding solutions coded as natural binary numbers.
 */
public class NaturalBinaryDecoder extends BitVectorDecoder {

	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}

	public NaturalBinaryDecoder(double min, double max, int bits, int n) {
		super(min, max, bits, n);
	}

	@Override
	public double[] decode(Antibody solution) {
		byte[] weightBits = solution.getBits(); 
		int len = 0;
		double[] numbers = new double[n];
		for (int i = 0; i < n; i++) {
			int k = 0;
			for (int j = 0; j < bits[i]; j++) {
				k += weightBits[len + j] * Math.pow(2, bits[i] - 1 - j);
			}

			numbers[i] = mins[i] + k / (Math.pow(2, bits[i]) - 1) * (maxs[i] - mins[i]);

			len += bits[i];
		}
		return numbers;
	}

	@Override
	public void decode(Antibody solution, double[] v) {
		double[] tmp = decode(solution);
		for (int i = 0; i < tmp.length; i++) {
			v[i] = tmp[i];
		}
	}

}
