package hr.fer.zemris.optjava.dz9.algorithm;

import java.util.HashSet;
import java.util.Set;

public class Chromosome {
	public double[] solution;
	public double[] objective;
	public Set<Chromosome> dominating;
	private double crowdingDistance;
	public int n;

	public void reduceDomination() {
		for (Chromosome c : dominating) {
			c.n--;
		}
		dominating = new HashSet<>();
	}

	public double getCrowdingDistance() {
		return crowdingDistance;
	}

	public void setCrowdingDistance(double crowdingDistance) {
		this.crowdingDistance = crowdingDistance;
	}

}
