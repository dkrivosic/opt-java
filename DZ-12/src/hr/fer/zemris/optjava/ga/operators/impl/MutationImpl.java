package hr.fer.zemris.optjava.ga.operators.impl;

import hr.fer.zemris.optjava.ga.operators.abs.IMutation;
import hr.fer.zemris.optjava.ga.struct.Chromosome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class MutationImpl implements IMutation {
	private double pIn;
	private double pLUT;
	private int numberOfVariables;
	private IRNG rng;

	public MutationImpl(double pIn, double pLUT, int numberOfVariables) {
		super();
		this.pIn = pIn;
		this.pLUT = pLUT;
		this.numberOfVariables = numberOfVariables;
		this.rng = RNG.getRNG();
	}

	@Override
	public void mutate(Chromosome c) {
		int numberOfCLBs = c.getNumberOfCLBs();
		int numberOfCLBInputs = c.getNumberOfCLBInputs();
		int LUTSize = c.getLUTSize();

		for (int clb = 0; clb < numberOfCLBs; clb++) {
			int index = clb * (numberOfCLBInputs + LUTSize);
			for (int i = 0; i < numberOfCLBInputs; i++) {
				if (rng.nextDouble() < pIn) {
					c.getData()[index + i] = rng.nextInt(0, numberOfVariables + clb);
				}
			}
			index += numberOfCLBInputs;
			for (int i = 0; i < LUTSize; i++) {
				if (rng.nextDouble() < pLUT) {
					c.getData()[index + i] = 1 - c.getData()[index + i];
				}
			}
		}
	}

}
