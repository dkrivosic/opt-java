package hr.fer.zemris.optjava.dz9;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;
import hr.fer.zemris.optjava.dz9.algorithm.IMOOPAlgorithm;
import hr.fer.zemris.optjava.dz9.algorithm.NSGA;
import hr.fer.zemris.optjava.dz9.crossover.ArithmeticalCrossover;
import hr.fer.zemris.optjava.dz9.crossover.ICrossover;
import hr.fer.zemris.optjava.dz9.functions.Function2;
import hr.fer.zemris.optjava.dz9.functions.IFunction;
import hr.fer.zemris.optjava.dz9.functions.Identity;
import hr.fer.zemris.optjava.dz9.functions.InRange;
import hr.fer.zemris.optjava.dz9.functions.QuadraticFunction;
import hr.fer.zemris.optjava.dz9.moop.Evaluator;
import hr.fer.zemris.optjava.dz9.moop.MOOPProblem;
import hr.fer.zemris.optjava.dz9.moop.MOOPProblemImpl;
import hr.fer.zemris.optjava.dz9.mutation.IMutation;
import hr.fer.zemris.optjava.dz9.mutation.Mutation;
import hr.fer.zemris.optjava.dz9.selection.ISelection;
import hr.fer.zemris.optjava.dz9.selection.RouletteWheelSelection;

public class MOOP {

	public static void main(String[] args) throws IOException {
		if (args.length != 4) {
			System.err.println("4 arguments expected: problem number, population size, type, max iterations");
			System.exit(0);
		}

		int fja = Integer.parseInt(args[0]);
		int populationSize = Integer.parseInt(args[1]);
		boolean decisionSpace = true;
		if (args[2].equals("objective-space")) {
			decisionSpace = false;
		}
		int maxIterations = Integer.parseInt(args[3]);

		MOOPProblem moop = null;
		InRange hardConstraints = null;

		if (fja == 1) {
			int dimension = 4;
			IFunction[] functions = new IFunction[] { new QuadraticFunction(0), new QuadraticFunction(1),
					new QuadraticFunction(2), new QuadraticFunction(3) };
			moop = new MOOPProblemImpl(dimension, functions);
			double[] xmin = new double[dimension];
			double[] xmax = new double[dimension];
			Arrays.fill(xmin, -5);
			Arrays.fill(xmax, 5);
			hardConstraints = new InRange(xmin, xmax);
		} else if (fja == 2) {
			int dimension = 2;
			IFunction[] functions = new IFunction[] { new Identity(0), new Function2() };
			moop = new MOOPProblemImpl(dimension, functions);
			double[] xmin = new double[dimension];
			double[] xmax = new double[dimension];
			xmin[0] = 0.1;
			xmax[0] = 1;
			xmin[1] = 0;
			xmax[1] = 5;
			hardConstraints = new InRange(xmin, xmax);
		} else {
			System.err.println("Wrong problem number");
			System.exit(0);
		}

		double epsilon = 0.9;
		double sigma = 4;
		double sigmaShare = 0.1;
		double alpha = 3;
		Evaluator evaluator = new Evaluator(moop, epsilon, decisionSpace, alpha, sigmaShare);
		ISelection selection = new RouletteWheelSelection();
		ICrossover crossover = new ArithmeticalCrossover(0.5);
		IMutation mutation = new Mutation(sigma);

		IMOOPAlgorithm algorithm = new NSGA(populationSize, hardConstraints, moop, evaluator, maxIterations, selection,
				crossover, mutation);

		List<Chromosome> population = algorithm.run();

		List<List<Chromosome>> fronts = evaluator.evaluatePopulation(population);
		for (List<Chromosome> front : fronts) {
			System.out.print(front.size() + " ");
		}
		System.out.println();

		// Write to file
		PrintWriter writerS = new PrintWriter("res/izlaz-dec.txt");
		PrintWriter writerO = new PrintWriter("res/izlaz-obj.txt");
		for (List<Chromosome> front : fronts) {
			for (Chromosome c : front) {
				String str = "";
				for (double d : c.solution) {
					str += d + " ";
				}
				writerS.write(str + "\n");
				str = "";
				for (double d : c.objective) {
					str += d + " ";
				}
				writerO.write(str + "\n");
			}
		}
		writerS.close();
		writerO.close();

	}
}
