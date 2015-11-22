package hr.fer.zemris.optjava.dz7.algorithm.clonalg;

import java.util.List;

import hr.fer.zemris.optjava.dz7.decoder.IDecoder;
import hr.fer.zemris.optjava.dz7.neural_network.INeuralNetwork;

/**
 * Class used for evaluating antibodies.
 */
public class Evaluator {
	private INeuralNetwork neuralNetwork;
	private IDecoder<Antibody> decoder;

	public Evaluator(IDecoder<Antibody> decoder, INeuralNetwork neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
		this.decoder = decoder;
	}

	/**
	 * Evaluates population of antibodies. It decodes each antibody and uses
	 * neural network to calculate error.
	 */
	public void evaluate(List<Antibody> population) {
		for (Antibody ab : population) {
			double[] weights = decoder.decode(ab);
			double error = neuralNetwork.getError(weights);
			ab.setAffinity(1.0 / error);
		}
	}

}
