package hr.fer.zemris.optjava.dz7.neural_network;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.dz7.data.IReadOnlyDataset;

/**
 * Feedforward artificial neural network.
 */
public class FFANN implements INeuralNetwork {
	private IReadOnlyDataset dataset;
	private int weightsCount;
	private List<List<Neuron>> network;

	/**
	 * @param neuronsPerLayer
	 *            Each element represents layer size. Array length is equal to
	 *            number of layers.
	 * @param functions
	 *            Transfer function for each layer.
	 * @param dataset
	 *            Dataset.
	 */
	public FFANN(int[] neuronsPerLayer, ITransferFunction[] functions, IReadOnlyDataset dataset) {
		this.dataset = dataset;

		this.weightsCount = 0;
		for (int i = 0; i < neuronsPerLayer.length - 1; i++) {
			weightsCount += (neuronsPerLayer[i] + 1) * neuronsPerLayer[i + 1];
		}

		// Create neurons
		network = new ArrayList<>();
		List<Neuron> layer = new ArrayList<>();
		// input layer
		ITransferFunction identity = new IdentityFunction();
		Neuron bias = new Neuron(identity);
		bias.addValue(1);
		layer.add(bias);

		for (int i = 0; i < neuronsPerLayer[0]; i++) {
			layer.add(new Neuron(identity));
		}
		network.add(layer);
		// other layers
		for (int i = 1; i < neuronsPerLayer.length; i++) {
			layer = new ArrayList<>();
			if (i != neuronsPerLayer.length - 1) {
				bias = new Neuron(identity);
				bias.addValue(1);
				layer.add(bias);
			}
			for (int j = 0; j < neuronsPerLayer[i]; j++) {
				layer.add(new Neuron(functions[i - 1]));
			}
			network.add(layer);
		}

	}

	@Override
	public int getWeightsCount() {
		return weightsCount;
	}

	@Override
	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		// Set inputs to input layer
		List<Neuron> inputLayer = network.get(0);
		for (int i = 1; i < inputLayer.size(); i++) {
			inputLayer.get(i).setValue(inputs[i - 1]);
		}
		
		int offset = 0;
		for (int layer = 1; layer < network.size() - 1; layer++) {
			int n = network.get(layer - 1).size();
			int m = network.get(layer).size();
			for (int i = 1; i < m; i++) {
				network.get(layer).get(i).reset();
				for (int j = 0; j < n; j++) {
					network.get(layer).get(i).addValue(weights[offset + j] * network.get(layer - 1).get(j).getOutput());
				}
				offset += n;
			}
		}

		// Calculate output of output layer
		List<Neuron> outputLayer = network.get(network.size() - 1);
		List<Neuron> lastHiddenLayer = network.get(network.size() - 2);
		for (int i = 0; i < outputLayer.size(); i++) {
			outputLayer.get(i).reset();
			for (int j = 0; j < lastHiddenLayer.size(); j++) {
				outputLayer.get(i).addValue(weights[offset + j] * lastHiddenLayer.get(j).getOutput());
			}
			offset += lastHiddenLayer.size();
		}

		// Copy outputs of output layer to array
		for (int i = 0; i < outputLayer.size(); i++) {
			outputs[i] = outputLayer.get(i).getOutput();
		}

	}

	@Override
	public double getError(double[] weights) {
		double error = 0;
		for (int i = 0; i < dataset.numberOfSamples(); i++) {
			double[] input = dataset.getInputs()[i];
			double[] expected = dataset.getOutputs()[i];
			double[] output = new double[expected.length];
			
			this.calcOutputs(input, weights, output);
			

			for (int j = 0; j < output.length; j++) {
				error += Math.pow(expected[j] - output[j], 2);
			}
		}

		return error / dataset.numberOfSamples();
	}

}
