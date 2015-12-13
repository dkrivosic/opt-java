package hr.fer.zemris.optjava.dz8.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TDNNLaserData implements IReadOnlyDataset {
	private double[][] input;
	private double[][] output;

	private TDNNLaserData(double[][] input, double[][] output) {
		this.input = input;
		this.output = output;
	}

	/**
	 * Factory method for <code>TDNNLaserData</code>.
	 * 
	 * @param file
	 *            path to file containing data.
	 * @param l
	 *            number of elements from input series in input layer.
	 * @param a
	 *            Number of elements from input series used for generating
	 *            training examples. If -1 is passed, all of the elements will
	 *            be used.
	 * @return TDNNLaserData object containing data.
	 * @throws IOException
	 */
	public static TDNNLaserData loadData(String file, int l, int a) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		List<Integer> allData = new ArrayList<>();
		String line = reader.readLine();
		while (line != null) {
			allData.add(Integer.parseInt(line.trim()));
			line = reader.readLine();
		}
		reader.close();

		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int x : allData) {
			if (x < min) {
				min = x;
			}
			if (x > max) {
				max = x;
			}
		}
		List<Double> normalizedData = new ArrayList<>();
		for (int x : allData) {
			normalizedData.add(2.0 * (x - min) / (max - min) - 1);
		}

		if (a == -1)
			a = normalizedData.size();

		double[][] input = new double[a - l][l];
		double[][] output = new double[a - l][1];
		for (int i = 0; i < a - l - 1; i++) {
			for(int j = 0; j < l; j++) {
				input[i][j] = normalizedData.get(i + j);
			}
			output[i][0] = normalizedData.get(i + l);
		}
		
		return new TDNNLaserData(input, output);
	}

	@Override
	public int numberOfSamples() {
		return input.length;
	}

	@Override
	public double[][] getInputs() {
		return input;
	}

	@Override
	public double[][] getOutputs() {
		return output;
	}

}
