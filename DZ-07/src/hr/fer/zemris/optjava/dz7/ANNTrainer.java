package hr.fer.zemris.optjava.dz7;

import java.util.Arrays;


import hr.fer.zemris.optjava.dz7.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.algorithm.clonalg.Antibody;
import hr.fer.zemris.optjava.dz7.algorithm.clonalg.CLONALG;
import hr.fer.zemris.optjava.dz7.algorithm.pso.DecreasingIWGlobalPSO;
import hr.fer.zemris.optjava.dz7.algorithm.pso.DecreasingIWLocalPSO;
import hr.fer.zemris.optjava.dz7.data.IReadOnlyDataset;
import hr.fer.zemris.optjava.dz7.data.IrisDataset;
import hr.fer.zemris.optjava.dz7.decoder.GreyBinaryDecoder;
import hr.fer.zemris.optjava.dz7.decoder.IDecoder;
import hr.fer.zemris.optjava.dz7.neural_network.FFANN;
import hr.fer.zemris.optjava.dz7.neural_network.INeuralNetwork;
import hr.fer.zemris.optjava.dz7.neural_network.ITransferFunction;
import hr.fer.zemris.optjava.dz7.neural_network.SigmoidTransferFunction;

public class ANNTrainer {

	public static void main(String[] args) {
		if (args.length != 5) {
			System.err.println("5 arguments expected: file, algorithm(pso-a, pso-b or clonalg),"
					+ " swarm size, acceptable error, max iterations");
			System.exit(0);
		}

		IReadOnlyDataset dataset = IrisDataset.readFile(args[0]);
		int populationSize = Integer.parseInt(args[2]);
		double merr = Double.parseDouble(args[3]);
		int maxIterations = Integer.parseInt(args[4]);

		INeuralNetwork neuralNetwork = new FFANN(new int[] { 4, 5, 3, 3 }, new ITransferFunction[] {
				new SigmoidTransferFunction(), new SigmoidTransferFunction(), new SigmoidTransferFunction() }, dataset);

		int dimension = neuralNetwork.getWeightsCount();
		// PSO algorithm parameters
		double wmax = 0.9;
		double wmin = 0.1;
		double[] xmin = new double[dimension];
		Arrays.fill(xmin, -10);
		double[] xmax = new double[dimension];
		Arrays.fill(xmax, 10);
		double[] vmin = new double[dimension];
		Arrays.fill(vmin, -2);
		double[] vmax = new double[dimension];
		Arrays.fill(vmax, 2);

		// CLONALG parameters
		int bitsPerVariable = 10;
		int bitVectorLength = bitsPerVariable * dimension;
		double rho = 0.15;
		double beta = 5; // 5
		int d = 10;
		IDecoder<Antibody> decoder = new GreyBinaryDecoder(-10, 10, bitsPerVariable, dimension);

		IOptAlgorithm algorithm = null;
		if (args[1].equals("pso-a")) {
			algorithm = new DecreasingIWGlobalPSO(populationSize, maxIterations, dimension, xmin, xmax, vmin, vmax,
					neuralNetwork, merr, 2.0, 2.0, wmin, wmax);
		} else if (args[1].startsWith("pso-b")) {
			String[] tmp = args[1].split("-");
			int neighboursNumber = Integer.parseInt(tmp[2]);
			algorithm = new DecreasingIWLocalPSO(populationSize, maxIterations, dimension, xmin, xmax, vmin, vmax,
					neuralNetwork, merr, 2, 2, wmin, wmax, neighboursNumber);
		} else if (args[1].equals("clonalg")) {
			algorithm = new CLONALG(neuralNetwork, beta, bitVectorLength, populationSize, d, rho, maxIterations,
					decoder, merr);
		} else {
			System.err.println("Unknown algorithm.");
			System.exit(0);
		}

		System.out.print("Learning");
		double[] weights = algorithm.run();
		System.out.println();
		System.out.println("error = " + neuralNetwork.getError(weights));
		System.out.println("List of misclassified examples:");

		// Checking each example
		double[][] inputs = dataset.getInputs();
		double[][] expected = dataset.getOutputs();
		int samples = dataset.numberOfSamples();
		int misclassified = 0;

		for (int i = 0; i < samples; i++) {
			double[] output = new double[expected[i].length];
			neuralNetwork.calcOutputs(inputs[i], weights, output);
			for (int j = 0; j < output.length; j++) {
				if (output[j] >= 0.5)
					output[j] = 1;
				else
					output[j] = 0;
			}

			for (int j = 0; j < output.length; j++) {
				if (output[j] != expected[i][j]) {
					misclassified++;
					System.out.println("input: " + toString(inputs[i]) + " expected: " + toString(expected[i])
							+ " output: " + toString(output));
					break;
				}
			}
		}
		System.out.println("(total: " + misclassified + ")");
	}

	private static String toString(double[] arr) {
		String str = "(";
		for (int i = 0; i < arr.length; i++) {
			str += arr[i] + " ";
		}
		str = str.trim();
		return str + ")";
	}
}
