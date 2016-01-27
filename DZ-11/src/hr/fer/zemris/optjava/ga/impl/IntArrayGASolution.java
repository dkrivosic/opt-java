package hr.fer.zemris.optjava.ga.impl;

import java.util.Arrays;

import hr.fer.zemris.optjava.ga.generic.GASolution;

/**
 * Genetic algorithm solution represented as array of integers.
 */
public class IntArrayGASolution extends GASolution<int[]> {

	public IntArrayGASolution(int[] data) {
		this.data = data;
	}

	public IntArrayGASolution(int n) {
		this.data = new int[n];
	}

	@Override
	public GASolution<int[]> duplicate() {
		int[] dataCopy = Arrays.copyOf(this.data, this.data.length);

		return new IntArrayGASolution(dataCopy);
	}

}
