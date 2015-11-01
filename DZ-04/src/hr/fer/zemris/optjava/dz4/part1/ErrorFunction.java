package hr.fer.zemris.optjava.dz4.part1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class ErrorFunction implements IFunction {
	private Matrix x;
	private Matrix y;
	private int m;

	private ErrorFunction(Matrix x, Matrix y, int m) {
		this.x = x;
		this.y = y;
		this.m = m;
	}

	/**
	 * Factory method that reads training examples from file.
	 */
	public static ErrorFunction readFromFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		String line = reader.readLine();
		List<String> lines = new ArrayList<>();
		while (line != null && !line.isEmpty()) {
			if (!line.startsWith("#")) {
				lines.add(line);
			}
			line = reader.readLine();
		}
		reader.close();

		int m = lines.size();
		int n = 5;
		double xArr[][] = new double[m][n];
		double yArr[][] = new double[m][1];

		for (int i = 0; i < m; i++) {
			String str = lines.get(i).replaceAll("[,\\[\\]]", "");
			str = str.trim();
			String tmp[] = str.split("( )+");
			for (int j = 0; j < n; j++) {
				xArr[i][j] = Double.parseDouble(tmp[j]);
			}
			yArr[i][0] = Double.parseDouble(tmp[n]);
		}

		Matrix x = new Matrix(xArr);
		Matrix y = new Matrix(yArr);

		return new ErrorFunction(x, y, m);
	}

	public double valueAt(double[] solution) {
		double err = 0;
		double a = solution[0];
		double d = solution[1];
		double b = solution[2];
		double e = solution[3];
		double c = solution[4];
		double f = solution[5];

		for (int i = 0; i < m; i++) {
			double x1 = x.get(i, 0);
			double x2 = x.get(i, 1);
			double x3 = x.get(i, 2);
			double x4 = x.get(i, 3);
			double x5 = x.get(i, 4);

			err += Math.pow(a * x1 + b * Math.pow(x1, 3) * x2 + c * Math.pow(Math.E, d * x3) * (1 + Math.cos(e * x4))
					+ f * x4 * Math.pow(x5, 2) - y.get(i, 0), 2);
		}

		return err / m;
	}

}
