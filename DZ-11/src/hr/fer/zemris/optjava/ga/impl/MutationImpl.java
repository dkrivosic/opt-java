package hr.fer.zemris.optjava.ga.impl;

import hr.fer.zemris.optjava.ga.generic.GASolution;
import hr.fer.zemris.optjava.ga.generic.IMutation;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class MutationImpl implements IMutation<int[]> {
	private int colChange;
	private int posChange;
	private double pMutation;
	private IRNG rng;

	public MutationImpl(int colChange, int posChange, double pMutation) {
		super();
		this.colChange = colChange;
		this.posChange = posChange;
		this.pMutation = pMutation;
		this.rng = RNG.getRNG();
	}

	@Override
	public void mutate(GASolution<int[]> solution) {
		int[] data = solution.getData();

		int index = 1;
		int n = (data.length - 1) / 5;

		for (int i = 0; i < n; i++) {
			if (rng.nextDouble() < pMutation) {
				data[index] = data[index] + rng.nextInt(-posChange, posChange + 1) ;
				data[index + 1] = data[index + 1] + rng.nextInt(-posChange, posChange + 1);
				data[index + 4] = data[index + 4] + rng.nextInt(-colChange, colChange + 1);
			}

			index += 5;
		}
	}

}
