package hr.fer.zemris.optjava.dz2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class Prijenosna {

	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
			System.out.println("3 arguments expected.");
			System.exit(0);
		}

		String method = args[0];
		int maxIterations = Integer.parseInt(args[1]);
		String path = args[2];

		// Reading
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

		Matrix solution = new Matrix(6, 1);
		for (int i = 0; i < 6; i++)
			solution.set(i, 0, 0);

		Matrix coef = Matrix.random(6, 1);
		ErrorFunctionPrijenosna err = new ErrorFunctionPrijenosna(x, y, m);

		if (method.equals("grad")) {
			coef = NumOptAlgorithms.gradientDescent(err, maxIterations, coef);
			// coef.print(n, 1);
		} else {
			coef = NumOptAlgorithms.newtonsMethod(err, maxIterations, coef);
			// x.print(n, 1);
		}

		System.out.println("final solution: ");
		coef.print(coef.getRowDimension(), coef.getColumnDimension());

		System.out.println("error = " + err.getFunctionValue(coef));

	}
}
