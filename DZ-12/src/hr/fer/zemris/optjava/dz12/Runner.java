package hr.fer.zemris.optjava.dz12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.optjava.bool.BooleanExpressionSolver;
import hr.fer.zemris.optjava.ga.GeneticAlgorithm;
import hr.fer.zemris.optjava.ga.IOptAlgorithm;
import hr.fer.zemris.optjava.ga.OptAlgorithmJob;
import hr.fer.zemris.optjava.ga.operators.abs.ICrossover;
import hr.fer.zemris.optjava.ga.operators.abs.IMutation;
import hr.fer.zemris.optjava.ga.operators.abs.ISelection;
import hr.fer.zemris.optjava.ga.operators.impl.DiscreteCrossover;
import hr.fer.zemris.optjava.ga.operators.impl.MutationImpl;
import hr.fer.zemris.optjava.ga.operators.impl.RouletteWheelSelection;
import hr.fer.zemris.optjava.ga.struct.Chromosome;
import hr.fer.zemris.optjava.ga.struct.Evaluator;
import hr.fer.zemris.optjava.rng.EVOThread;

public class Runner {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("3 arguments expected:\nNumber of CLB inputs\nNumber of CLBs\nBoolean function");
			System.exit(0);
		}

		int numberOfCLBInputs = Integer.parseInt(args[0]);
		int numberOfCLBs = Integer.parseInt(args[1]);

		int maxGenerations = 1000;
		int populationSize = 100;
		int frequency = 100;

		BooleanExpressionSolver solver1 = new BooleanExpressionSolver(args[2]);
		BooleanExpressionSolver solver2 = new BooleanExpressionSolver(args[2]);
		BooleanExpressionSolver solver3 = new BooleanExpressionSolver(args[2]);
		BooleanExpressionSolver solver4 = new BooleanExpressionSolver(args[2]);

		Evaluator evaluator1 = new Evaluator(solver1);
		Evaluator evaluator2 = new Evaluator(solver2);
		Evaluator evaluator3 = new Evaluator(solver3);
		Evaluator evaluator4 = new Evaluator(solver4);

		ISelection selection1 = new RouletteWheelSelection();
		ISelection selection2 = new RouletteWheelSelection();
		ISelection selection3 = new RouletteWheelSelection();
		ISelection selection4 = new RouletteWheelSelection();

		ICrossover crossover1 = new DiscreteCrossover();
		ICrossover crossover2 = new DiscreteCrossover();
		ICrossover crossover3 = new DiscreteCrossover();
		ICrossover crossover4 = new DiscreteCrossover();

		int numberOfVariables = solver1.getNumberOfVariables();

		double pIn1 = 0.05;
		double pLUT1 = 0.05;
		IMutation mutation1 = new MutationImpl(pIn1, pLUT1, numberOfVariables);
		double pIn2 = 0.05;
		double pLUT2 = 0.05;
		IMutation mutation2 = new MutationImpl(pIn2, pLUT2, numberOfVariables);
		double pIn3 = 0.05;
		double pLUT3 = 0.05;
		IMutation mutation3 = new MutationImpl(pIn3, pLUT3, numberOfVariables);
		double pIn4 = 0.05;
		double pLUT4 = 0.05;
		IMutation mutation4 = new MutationImpl(pIn4, pLUT4, numberOfVariables);

		LinkedBlockingQueue<Chromosome> solution = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<Chromosome> q1 = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<Chromosome> q2 = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<Chromosome> q3 = new LinkedBlockingQueue<>();
		LinkedBlockingQueue<Chromosome> q4 = new LinkedBlockingQueue<>();

		IOptAlgorithm algorithm1 = new GeneticAlgorithm(maxGenerations, populationSize, numberOfCLBInputs, numberOfCLBs,
				solver1, evaluator1, q1, q4, solution, selection1, crossover1, mutation1, frequency);
		IOptAlgorithm algorithm2 = new GeneticAlgorithm(maxGenerations, populationSize, numberOfCLBInputs, numberOfCLBs,
				solver2, evaluator2, q2, q1, solution, selection2, crossover2, mutation2, frequency);
		IOptAlgorithm algorithm3 = new GeneticAlgorithm(maxGenerations, populationSize, numberOfCLBInputs, numberOfCLBs,
				solver3, evaluator3, q3, q2, solution, selection3, crossover3, mutation3, frequency);
		IOptAlgorithm algorithm4 = new GeneticAlgorithm(maxGenerations, populationSize, numberOfCLBInputs, numberOfCLBs,
				solver4, evaluator4, q4, q3, solution, selection4, crossover4, mutation4, frequency);

		OptAlgorithmJob[] jobs = new OptAlgorithmJob[] { new OptAlgorithmJob(algorithm1),
				new OptAlgorithmJob(algorithm2), new OptAlgorithmJob(algorithm3), new OptAlgorithmJob(algorithm4) };

		EVOThread[] threads = new EVOThread[] { new EVOThread(jobs[0]), new EVOThread(jobs[1]), new EVOThread(jobs[2]),
				new EVOThread(jobs[3]) };

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		Chromosome sol = null;
		try {
			sol = solution.take();
		} catch (InterruptedException e) {
		}

		for (OptAlgorithmJob job : jobs) {
			job.stopAlgorithm();
		}

		
		System.out.println("function: " + Arrays.asList(solver1.getAllValues()).toString());
		System.out.println(sol);
		showSolution(sol, solver1);

	}

	private static void showSolution(Chromosome solution, BooleanExpressionSolver solver) {
		List<String> variables = new ArrayList<>(solver.getVariables());
		int numberOfVariables = variables.size();
		for (int i = 0; i < solution.getNumberOfCLBs(); i++) {
			variables.add("CLB_" + i);
		}

		Queue<Integer> toWrite = new LinkedList<>();
		toWrite.add(solution.bestCLB + numberOfVariables);
		while (!toWrite.isEmpty()) {
			int index = toWrite.poll();
			if (index < numberOfVariables) {
				continue;
			}

			System.out.print(variables.get(index) + " inputs: ");
			int in = (index - numberOfVariables) * (solution.getNumberOfCLBInputs() + solution.getLUTSize());
			System.out.print(variables.get(solution.getData()[in]) + ", ");
			System.out.println(variables.get(solution.getData()[in + 1]));

			toWrite.add(solution.getData()[in]);
			toWrite.add(solution.getData()[in + 1]);
		}
	}

}
