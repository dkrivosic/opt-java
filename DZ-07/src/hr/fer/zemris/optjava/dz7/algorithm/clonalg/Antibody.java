package hr.fer.zemris.optjava.dz7.algorithm.clonalg;

import java.util.Arrays;
import java.util.Random;

/**
 * Antibody.
 */
public class Antibody implements Comparable<Antibody> {
	private byte[] bits;
	private double affinity;

	public Antibody(byte[] bits) {
		this.bits = bits;
	}

	public Antibody(int l, Random random) {
		bits = new byte[l];
		for (int i = 0; i < l; i++) {
			bits[i] = (byte) random.nextInt(2);
		}
	}

	public byte[] getBits() {
		return bits;
	}

	public Antibody clone() {
		byte[] copyBits = Arrays.copyOf(bits, bits.length);
		return new Antibody(copyBits);
	}

	public double getAffinity() {
		return affinity;
	}

	public void setAffinity(double affinity) {
		this.affinity = affinity;
	}

	@Override
	public int compareTo(Antibody o) {
		if (affinity > o.getAffinity()) {
			return -1;
		} else if (affinity < o.getAffinity()) {
			return 1;
		}
		return 0;
	}

}
