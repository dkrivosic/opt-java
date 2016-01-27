package hr.fer.zemris.optjava.ga.struct;

import java.util.Arrays;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * Genetic algorithm solution.
 */
public class Chromosome {
	public int error;
	public int bestCLB;
	private int[] data;
	private int numberOfCLBInputs;
	private int LUTSize;
	private int numberOfCLBs;

	public Chromosome(int[] data, int numberOfCLBInputs, int numberOfCLBs) {
		super();
		this.data = data;
		this.numberOfCLBInputs = numberOfCLBInputs;
		LUTSize = (int) Math.pow(2, numberOfCLBInputs);
		this.numberOfCLBs = numberOfCLBs;
	}

	public Chromosome(int numberOfCLBInputs, int numberOfCLBs) {
		super();
		int dataSize = numberOfCLBs * (numberOfCLBInputs + (int) Math.pow(2, numberOfCLBInputs));
		this.data = new int[dataSize];
		this.numberOfCLBInputs = numberOfCLBInputs;
		this.LUTSize = (int) Math.pow(2, numberOfCLBInputs);
		this.numberOfCLBs = numberOfCLBs;
	}

	public int[] getData() {
		return data;
	}

	public int getNumberOfCLBInputs() {
		return numberOfCLBInputs;
	}

	public int getLUTSize() {
		return LUTSize;
	}

	public int getNumberOfCLBs() {
		return numberOfCLBs;
	}

	/**
	 * Returns array of CLB outputs for given inputs. Array length is equal to
	 * number of CLBs.
	 * 
	 * @param input
	 *            input values
	 * @return CLB outputs
	 */
	public int[] calculateOutputs(int[] input) {
		int numberOfVariables = input.length;
		int[] out = new int[numberOfVariables + numberOfCLBs];
		for (int i = 0; i < numberOfVariables; i++) {
			out[i] = input[i];
		}

		for (int clb = 0; clb < numberOfCLBs; clb++) {
			int index = clb * (numberOfCLBInputs + LUTSize);

			String binary = "";
			for (int i = 0; i < numberOfCLBInputs; i++) {
				binary += String.valueOf(out[data[index + i]]);
			}

			int lutIndex = index + numberOfCLBInputs + Integer.parseInt(binary, 2);
			out[numberOfVariables + clb] = data[lutIndex];

		}

		return Arrays.copyOfRange(out, numberOfVariables, out.length);
	}

	/**
	 * Randomly sets the values of data array.
	 */
	public void randomize(int numberOfVariables) {
		IRNG rng = RNG.getRNG();
		for (int clb = 0; clb < numberOfCLBs; clb++) {
			int index = clb * (numberOfCLBInputs + LUTSize);
			for (int i = 0; i < numberOfCLBInputs; i++) {
				data[index + i] = rng.nextInt(0, numberOfVariables + clb);
			}
			index += numberOfCLBInputs;
			for (int i = 0; i < LUTSize; i++) {
				data[index + i] = rng.nextInt(0, 2);
			}
		}
	}

	@Override
	public String toString() {
		String str = "error: " + error + "\nbest CLB: " + bestCLB + "\ndata: ";
		for (int i = 0; i < data.length; i++) {
			str += data[i] + " ";
		}
		return str;
	}

}
