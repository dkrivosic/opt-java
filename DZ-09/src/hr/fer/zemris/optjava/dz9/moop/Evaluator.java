package hr.fer.zemris.optjava.dz9.moop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class Evaluator {
	private MOOPProblem moop;
	private double epsilon;
	private boolean decisionSpace;
	private double alpha;
	private double sigmaShare;
	private double[] xmin;
	private double[] xmax;

	public Evaluator(MOOPProblem moop, double epsilon, boolean decisionSpace, double alpha, double sigmaShare,
			double[] xmin, double[] xmax) {
		this.moop = moop;
		this.epsilon = epsilon;
		this.decisionSpace = decisionSpace;
		this.alpha = alpha;
		this.sigmaShare = sigmaShare;
		this.xmin = xmin;
		this.xmax = xmax;
	}

	public List<List<Chromosome>> evaluatePopulation(List<Chromosome> population) {
		for (Chromosome c : population) {
			moop.evaluateSolution(c.solution, c.objective);
		}

		List<List<Chromosome>> fronts = sort(population);
		double N = fronts.size();
		double Fmin = N + epsilon;

		for (List<Chromosome> front : fronts) {

			// Calculate shared fitness
			SharingFunction sh = new SharingFunction(sigmaShare, alpha);
			for (Chromosome c : front) {
				c.setFitness(Fmin - epsilon);
				double nc = 0;
				for (Chromosome k : population) {
					if (decisionSpace) {
						nc += sh.value(c.solution, k.solution, xmax, xmin);
					} else {
						nc += sh.value(c.objective, k.objective, xmax, xmin);
					}
				}
				c.setFitness(c.getFitness() / nc);
			}

			// Determine Fmin for the next front
			Fmin = Double.MAX_VALUE;
			for (Chromosome c : front) {
				if (c.getFitness() < Fmin) {
					Fmin = c.getFitness();
				}
			}

		}

		return fronts;
	}

	/**
	 * Non-dominated sorting. Method returns population divided in fronts.
	 */
	private List<List<Chromosome>> sort(List<Chromosome> population) {
		for (Chromosome c : population) {
			c.n = 0;
			c.dominating = new HashSet<>();
		}

		List<List<Chromosome>> fronts = new ArrayList<>();
		List<Chromosome> tmpPop = new ArrayList<>(population);

		int n = tmpPop.size();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Chromosome ci = tmpPop.get(i);
				Chromosome cj = tmpPop.get(j);
				if (isDominating(ci, cj)) {
					ci.dominating.add(cj);
				} else if (isDominating(cj, ci)) {
					ci.n++;
				}
			}
		}

		while (!tmpPop.isEmpty()) {
			n = tmpPop.size();
			List<Chromosome> front = new ArrayList<>();

			for (int i = 0; i < n; i++) {
				Chromosome c = tmpPop.get(i);
				if (c.n == 0) {
					front.add(c);
				}
			}

			for (Chromosome c : front) {
				c.reduceDomination();
			}

			fronts.add(front);
			tmpPop.removeAll(front);
		}

		return fronts;
	}

	/**
	 * Checks if chromosome c1 dominates chromosome c2.
	 * 
	 * @return true if c1 dominates c2, false otherwise.
	 */
	private boolean isDominating(Chromosome c1, Chromosome c2) {
		int n = c1.objective.length;
		boolean lessThan = false;
		for (int i = 0; i < n; i++) {
			if (c1.objective[i] > c2.objective[i]) {
				return false;
			} else if (c1.objective[i] < c2.objective[i]) {
				lessThan = true;
			}
		}
		return lessThan;
	}
}
