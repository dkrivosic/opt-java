package hr.fer.zemris.optjava.dz7.algorithm.clonalg;

import java.util.List;
import java.util.Random;

/**
 * Hypermutation.
 */
public class HypermutationOperator {
	private double rho;
	private int populationSize;
	private int bitVectorLength;

	public HypermutationOperator(double rho, int populationSize, int bitVectorLength) {
		this.rho = rho;
		this.populationSize = populationSize;
		this.bitVectorLength = bitVectorLength;
	}

	/**
	 * Hypermutates population of antibodies. Better antibodies are changed less
	 * than worse ones.
	 */
	public List<Antibody> hypermutate(List<Antibody> population) {
		double fmin = Double.MAX_VALUE;
		double fmax = Double.MIN_VALUE;
		for (Antibody ab : population) {
			if (ab.getAffinity() < fmin) {
				fmin = ab.getAffinity();
			}
			if (ab.getAffinity() > fmax) {
				fmax = ab.getAffinity();
			}
		}

		double tau = -(double) (populationSize - 1.0) / Math.log(1.0 - rho);

		Random random = new Random();
		for (int r = 0; r < populationSize; r++) {
			Antibody ab = population.get(r);
			int k = (int) (1 + bitVectorLength * (1 - Math.pow(Math.E, -r / tau)));

			byte[] bits = ab.getBits();
			for (int i = 0; i < k; i++) {
				int pos = random.nextInt(bitVectorLength);
				bits[pos] = (byte) (1 - bits[pos]);
			}
		}

		return population;
	}
}
