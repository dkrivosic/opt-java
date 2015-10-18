package hr.fer.zemris.optjava.dz2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

/**
 * Solves system of equations using gradient descent or Newton's method.
 */
public class Sustav {

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
			if(!line.startsWith("#")) {
				lines.add(line);
			}
			line = reader.readLine();
		}
		reader.close();
		
		int n = lines.size();
		double coef[][] = new double[n][n];
		double yArr[][] = new double[n][1];
		
		for(int i = 0; i < n; i++) {
			String str = lines.get(i).replaceAll("[,\\[\\]]", "");
			str = str.trim();
			String tmp[] = str.split("( )+");
			for(int j = 0; j < n; j++) {
				coef[i][j] = Double.parseDouble(tmp[j]);
			}
			yArr[i][0] = Double.parseDouble(tmp[n]);
		}

		Matrix coefficients = new Matrix(coef);
		Matrix y = new Matrix(yArr);
		Matrix x = Matrix.random(n, 1);
		ErrorFunctionSustav err = new ErrorFunctionSustav(coefficients, y, n);
		
		if(method.equals("grad")) {
			x = NumOptAlgorithms.gradientDescent(err, maxIterations, x);
			x.print(n, 1);
		} else {
			x = NumOptAlgorithms.newtonsMethod(err, maxIterations, x);
			x.print(n, 1);
		}

		System.out.println("error: " + err.getFunctionValue(x));
		
		
	}
}
