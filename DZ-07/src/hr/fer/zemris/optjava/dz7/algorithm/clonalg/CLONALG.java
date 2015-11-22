package hr.fer.zemris.optjava.dz7.algorithm.clonalg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.algorithm.IOptAlgorithm;
import hr.fer.zemris.optjava.dz7.decoder.IDecoder;
import hr.fer.zemris.optjava.dz7.neural_network.INeuralNetwork;

/**
 * Clonal Selection Algorithm. 
 */
public class CLONALG implements IOptAlgorithm {
	private INeuralNetwork neuralNetwork;
	private double beta;
	private int bitVectorLength;
	private int populationSize;
	private int d; // Number of new random antibodies
	private double rho;
	private int maxIterations;
	private IDecoder<Antibody> decoder;
	private double merr;

	public CLONALG(INeuralNetwork neuralNetwork, double beta, int bitVectorLength, int populationSize, int d,
			double rho, int maxIterations, IDecoder<Antibody> decoder, double merr) {
		this.neuralNetwork = neuralNetwork;
		this.beta = beta;
		this.bitVectorLength = bitVectorLength;
		this.populationSize = populationSize;
		this.d = d;
		this.rho = rho;
		this.maxIterations = maxIterations;
		this.decoder = decoder;
		this.merr = merr;
	}

	@Override
	public double[] run() {
		Random random = new Random();
		CloneOperator cloneOp = new CloneOperator();
		HypermutationOperator hypOp = new HypermutationOperator(rho, populationSize, bitVectorLength);
		Evaluator evaluator = new Evaluator(decoder, neuralNetwork);
		// Initialize population
		List<Antibody> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(new Antibody(bitVectorLength, random));
		}

		int iter = 0;
		double goodEnough = 1.0 / merr;
		int dots = maxIterations / 100;
		do {
			if(iter % dots == 0) {
				System.out.print(".");
			}
			
			// Evaluate population
			evaluator.evaluate(population);
			Collections.sort(population);

			// Clone and hypermutate
			List<Antibody> clones = cloneOp.clone(population, beta);
			evaluator.evaluate(clones); // ?????
			List<Antibody> phyp = hypOp.hypermutate(clones);
			evaluator.evaluate(phyp);
			Collections.sort(population);

			// Create new random antibodies
			List<Antibody> pBirth = new ArrayList<>();
			for (int i = 0; i < d; i++) {
				pBirth.add(new Antibody(bitVectorLength, random));
			}

			// New population
			population = new ArrayList<>();
			for (int i = 0; i < populationSize - d; i++) {
				population.add(phyp.get(i));
			}
			population.addAll(pBirth);

			iter++;
		} while (iter < maxIterations && population.get(0).getAffinity() < goodEnough);

		Antibody best = population.get(0);

		return decoder.decode(best);
	}

}
