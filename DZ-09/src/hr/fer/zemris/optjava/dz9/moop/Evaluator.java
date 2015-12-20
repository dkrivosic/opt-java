package hr.fer.zemris.optjava.dz9.moop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;

public class Evaluator {
	private MOOPProblem moop;
	private double epsilon;
	private boolean decisionSpace;
	private double alpha;
	private double sigmaShare;

	public Evaluator(MOOPProblem moop, double epsilon, boolean decisionSpace, double alpha, double sigmaShare) {
		this.moop = moop;
		this.epsilon = epsilon;
		this.decisionSpace = decisionSpace;
		this.alpha = alpha;
		this.sigmaShare = sigmaShare;
	}

	public List<List<Chromosome>> evaluatePopulation(List<Chromosome> population) {
		for (Chromosome c : population) {
			moop.evaluateSolution(c.solution, c.objective);
		}

		List<List<Chromosome>> fronts = sort(population);
		double N = fronts.size();
		double Fmin = N + epsilon;

		int dimension = 0;
		if (decisionSpace) {
			dimension = fronts.get(0).get(0).solution.length;
		} else {
			dimension = fronts.get(0).get(0).objective.length;
		}

		for (List<Chromosome> front : fronts) {
			// Find min and max values
			double[] xmin = new double[dimension];
			double[] xmax = new double[dimension];
			Arrays.fill(xmin, Integer.MAX_VALUE);
			Arrays.fill(xmax, Integer.MIN_VALUE);
			for (Chromosome c : front) {
				for (int i = 0; i < dimension; i++) {
					if (decisionSpace) {
						if (c.solution[i] < xmin[i])
							xmin[i] = c.solution[i];
						if (c.solution[i] > xmax[i])
							xmax[i] = c.solution[i];
					} else {
						if (c.objective[i] < xmin[i])
							xmin[i] = c.objective[i];
						if (c.objective[i] > xmax[i])
							xmax[i] = c.objective[i];
					}
				}
			}

			// Calculate shared fitness
			SharingFunction sh = new SharingFunction(sigmaShare, alpha);
			for (Chromosome c : front) {
				c.setFitness(Fmin - epsilon);
				double nc = 0;
				for (Chromosome k : front) {
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
