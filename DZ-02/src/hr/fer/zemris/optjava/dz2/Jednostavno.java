package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class Jednostavno {

	public static void main(String[] args) {
		if(args.length != 2 && args.length != 4) {
			System.out.println("Wrong number of arguments (2 or 4 expected).");
			System.exit(0);
		}
		
		IHFunction function;
		int maxIterations = Integer.parseInt(args[1]);
		Matrix x;
		if(args.length == 4) {
			double x1 = Double.parseDouble(args[2]);
			double x2 = Double.parseDouble(args[3]);
			x = new Matrix(new double[][]{{x1}, {x2}});
		} else {
			x = Matrix.random(2, 1);
		}
		
		switch(args[0]) {
		case "1a":
			function = new F1();
			x = NumOptAlgorithms.gradientDescent(function, maxIterations, x);
			break;
		case "1b":
			function = new F1();
			x = NumOptAlgorithms.newtonsMethod(function, maxIterations, x);
			break;
		case "2a":
			function = new F2();
			x = NumOptAlgorithms.gradientDescent(function, maxIterations, x);
			break;
		case "2b":
			function = new F2();
			x = NumOptAlgorithms.newtonsMethod(function, maxIterations, x);
			break;
		}
		
		System.out.println("x:");
		x.print(x.getRowDimension(), x.getColumnDimension());

	}

	
}
