package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class PassThroughDecoder implements IDecoder<SingleObjectiveSolution> {

	@Override
	public double[] decode(SingleObjectiveSolution solution) {
		DoubleArraySolution s = (DoubleArraySolution) solution;
		return s.values;
	}

	@Override
	public void decode(SingleObjectiveSolution solution, double[] v) {
		DoubleArraySolution s = (DoubleArraySolution) solution;
		int n = s.values.length;
		for (int i = 0; i < n; i++) {
			v[i] = s.values[i];
		}
	}
}
