package hr.fer.zemris.optjava.dz7.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for Iris dataset. Lines are in format (sepal_length, sepal_width,
 * petal_length, petal_width):(setosa, versicolor, virginica)
 */
public class IrisDataset implements IReadOnlyDataset {
	private double inputs[][];
	private double outputs[][];

	private IrisDataset(String path) {
		try {
			read(path);
		} catch (FileNotFoundException e) {
			System.err.println("File " + path + " not found.");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	public static IrisDataset readFile(String path) {
		return new IrisDataset(path);
	}

	private void read(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		List<String> lines = new ArrayList<>();
		String line = reader.readLine();
		while (line != null && !line.isEmpty()) {
			lines.add(line);
			line = reader.readLine();
		}
		reader.close();

		int n = lines.size();
		inputs = new double[n][4];
		outputs = new double[n][3];

		for (int i = 0; i < n; i++) {
			line = lines.get(i);
			line = line.replaceAll("[\\(\\)]", "");
			String tmp[] = line.split(":");
			String ins[] = tmp[0].split(",");
			String outs[] = tmp[1].split(",");
			for (int j = 0; j < ins.length; j++) {
				inputs[i][j] = Double.parseDouble(ins[j]);
			}
			for (int j = 0; j < outs.length; j++) {
				outputs[i][j] = Double.parseDouble(outs[j]);
			}
		}
	}

	@Override
	public double[][] getInputs() {
		return inputs;
	}

	@Override
	public double[][] getOutputs() {
		return outputs;
	}

	@Override
	public int numberOfSamples() {
		return outputs.length;
	}
}
