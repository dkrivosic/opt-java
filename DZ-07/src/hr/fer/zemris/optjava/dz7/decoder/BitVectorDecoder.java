package hr.fer.zemris.optjava.dz7.decoder;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz7.algorithm.clonalg.Antibody;


public abstract class BitVectorDecoder implements IDecoder<Antibody> {

	protected double[] mins; // lower bounds
	protected double[] maxs; // upper bounds
	protected int[] bits; // number of bits for variable
	protected int n; // number of variables
	protected int totalBits;

	public int getTotalBits() {
		return totalBits;
	}

	public BitVectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		this.mins = mins;
		this.maxs = maxs;
		this.bits = bits;
		this.n = n;
		this.totalBits = 0;
		for (int i = 0; i < bits.length; i++)
			this.totalBits += bits[i];
	}

	public BitVectorDecoder(double min, double max, int bits, int n) {
		this.n = n;
		this.mins = new double[n];
		this.maxs = new double[n];
		this.bits = new int[n];
		Arrays.fill(this.mins, min);
		Arrays.fill(this.maxs, max);
		Arrays.fill(this.bits, bits);

	}

}
