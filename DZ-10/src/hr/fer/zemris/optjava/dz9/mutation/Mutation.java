package hr.fer.zemris.optjava.dz9.mutation;

import java.util.Random;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class Mutation implements IMutation {
	private double sigma;
	private Random random;

	public Mutation(double sigma) {
		this.sigma = sigma;
		this.random = new Random();
	}

	@Override
	public Chromosome mutate(Chromosome c) {
		for(int i = 0; i < c.solution.length; i++) {
			c.solution[i] += random.nextGaussian() * sigma;
		}
		
		return c;
	}
	
	

}
