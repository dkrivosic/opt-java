package hr.fer.zemris.optjava.dz13;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import hr.fer.zemris.optjava.gp.algorithm.AntEvaluator;
import hr.fer.zemris.optjava.gp.algorithm.GeneticProgramming;
import hr.fer.zemris.optjava.gp.algorithm.IOPTAlgorithm;
import hr.fer.zemris.optjava.gp.ant.Ant;
import hr.fer.zemris.optjava.gp.crossover.Crossover;
import hr.fer.zemris.optjava.gp.crossover.ICrossover;
import hr.fer.zemris.optjava.gp.helpers.TreeGenerator;
import hr.fer.zemris.optjava.gp.helpers.World;
import hr.fer.zemris.optjava.gp.mutation.IMutation;
import hr.fer.zemris.optjava.gp.mutation.Mutation;
import hr.fer.zemris.optjava.gp.selection.ISelection;
import hr.fer.zemris.optjava.gp.selection.TournamentSelection;
import hr.fer.zemris.optjava.swing.AntTrailVisualization;

public class AntTrailGA {

	public static void main(String[] args) throws IOException {
		if (args.length != 5) {
			System.err.println("5 argumnts expected:\nPath to file containing map\nMaximum generations\n"
					+ "Population size\nAcceptable fitness\nPath to file to save best ant");
			System.exit(0);
		}
		String filename = args[0];
		int maxGenerations = Integer.parseInt(args[1]);
		int populationSize = Integer.parseInt(args[2]);
		double acceptableFitness = Double.parseDouble(args[3]);
		String saveFile = args[4];

		int maxInitialDepth = 6;
		Random random = new Random();
		double pReproduction = 0.01;
		double pMutation = 0.15;
		int maxNodes = 200;
		int maxMoves = 200;
		double plagiarismPunishment = 0.9;
		int maxDepth = 20;

		World.initialize(filename);
		TreeGenerator treeGenerator = new TreeGenerator();
		AntEvaluator evaluator = new AntEvaluator(maxMoves, plagiarismPunishment);
		ISelection selection = new TournamentSelection(7, random);
		IMutation mutation = new Mutation(treeGenerator, random, maxDepth);
		ICrossover crossover = new Crossover(random);

		IOPTAlgorithm algorithm = new GeneticProgramming(populationSize, maxInitialDepth, treeGenerator, maxGenerations,
				acceptableFitness, evaluator, pMutation, pReproduction, random, selection, mutation, crossover,
				maxNodes);

		Ant ant = algorithm.run();

		PrintWriter writer = new PrintWriter(saveFile, "UTF-8");
		writer.println(ant.toString());
		writer.close();
		
		AntTrailVisualization window = new AntTrailVisualization(filename, ant, evaluator);

		// Simulation
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
