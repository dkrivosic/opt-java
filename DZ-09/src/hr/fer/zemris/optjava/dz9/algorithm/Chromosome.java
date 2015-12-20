package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.HashSet;
import java.util.Set;

public class Chromosome {
	public double[] solution;
	public double[] objective;
	public Set<Chromosome> dominating;
	private double fitness;
	public int n;

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void reduceDomination() {
		for (Chromosome c : dominating) {
			c.n--;
		}
		dominating = new HashSet<>();
	}

}
