package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.algorithm.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.algorithm.ITempSchedule;
import hr.fer.zemris.optjava.dz3.algorithm.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.decoder.GreyBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.function.ErrorFunction;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbourhood.BitVectorNeighbourhood;
import hr.fer.zemris.optjava.dz3.neighbourhood.DoubleArrayNormNeighbourhood;
import hr.fer.zemris.optjava.dz3.neighbourhood.INeighbourhood;
import hr.fer.zemris.optjava.dz3.solution.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

public class SystemRegression {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("2 arguments expected.");
			System.exit(0);
		}

		final double alpha = 0.99;
		final int initialTemp = 1000;
		final int innerLimit = 1000;
		final int n = 6; // number of variables
		Random rand = new Random();

		IFunction function = ErrorFunction.readFromFile(args[0]);
		ITempSchedule tempSchedule = new GeometricTempSchedule(alpha, initialTemp, innerLimit);
		SingleObjectiveSolution solution;
		IDecoder<SingleObjectiveSolution> decoder;
		INeighbourhood<SingleObjectiveSolution> neighbourhood;
		IOptAlgorithm<SingleObjectiveSolution> algorithm;

		if (args[1].equals("decimal")) {
			DoubleArraySolution s = new DoubleArraySolution(n);
			s.randomize(rand, -5, 5);
			solution = s;

			decoder = new PassThroughDecoder();

			double[] deltas = new double[n];
			Arrays.fill(deltas, 0.1);
			neighbourhood = new DoubleArrayNormNeighbourhood(deltas);
		} else {
			String tmp[] = args[1].split(":");
			int bits = Integer.parseInt(tmp[1]);
			BitVectorSolution s = new BitVectorSolution(n * bits);
			s.randomize(rand);
			solution = s;

			decoder = new GreyBinaryDecoder(-5, 5, bits, n);
			neighbourhood = new BitVectorNeighbourhood();
		}

		algorithm = new SimulatedAnnealing<SingleObjectiveSolution>(decoder, neighbourhood, solution, function, true,
				tempSchedule);

		solution = algorithm.run();

		System.out.println(solutionToDecimal(decoder, solution));
		System.out.println("error = " + function.valueAt(decoder.decode(solution)));

	}

	private static String solutionToDecimal(IDecoder<SingleObjectiveSolution> decoder,
			SingleObjectiveSolution solution) {
		String str = "[";
		double[] array = decoder.decode(solution);
		for (int i = 0; i < array.length; i++) {
			str += array[i] + " ";
		}
		return str + "]";
	}

}
