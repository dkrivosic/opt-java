package hr.fer.zemris.optjava.dz4.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

	private double bestFitness;
	private Chromosome bestChromosome;
	private List<Chromosome> individuals;
	private double lowestError;

	public Population() {
		this.individuals = new ArrayList<>();
		this.bestFitness = 0;
		this.bestChromosome = null;
	}

	public int size() {
		return individuals.size();
	}

	public void add(Chromosome c) {
		individuals.add(c);
	}

	public void add(List<Chromosome> c) {
		individuals.addAll(c);
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public double getLowestError() {
		return lowestError;
	}

	public Chromosome getBest() {
		return bestChromosome;
	}

	public List<Chromosome> getBest(int n) {
		Collections.sort(individuals);
		int size = this.size();
		return individuals.subList(size - n, size);
	}

	public List<Chromosome> getAll() {
		return individuals;
	}

	public void evaluate(IFunction function, boolean minimize) {
		int n = individuals.size();

		for (int i = 0; i < n; i++) {
			Chromosome c = individuals.get(i);
			if (minimize) {
				c.error = function.valueAt(c.values);
				c.fitness = 1 / c.error;
			} else {
				c.fitness = function.valueAt(c.values);
				c.error = 1 / c.fitness;
			}
			if (c.fitness > bestFitness) {
				bestFitness = c.fitness;
				bestChromosome = c;
				lowestError = c.error;
			}
		}
	}

}
