package hr.fer.zemris.optjava.dz9.moop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import hr.fer.zemris.optjava.dz9.algorithm.Chromosome;
import hr.fer.zemris.optjava.dz9.comparators.CrowdingSortComparator;
import hr.fer.zemris.optjava.dz9.comparators.ObjectiveFunctionComparator;

public class Evaluator {
	private MOOPProblem moop;
	private double[] fmax;
	private double[] fmin;
	private CrowdingSortComparator csComparator;

	public Evaluator(MOOPProblem moop, double[] fmax, double[] fmin) {
		this.moop = moop;
		this.fmax = fmax;
		this.fmin = fmin;
		csComparator = new CrowdingSortComparator();
	}

	/**
	 * Non-dominated sorting. Method returns population divided in fronts.
	 */
	public List<List<Chromosome>> nonDominatedSort(List<Chromosome> population) {
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

		updateDominance(population);

		return fronts;
	}

	private void updateDominance(List<Chromosome> population) {
		for (Chromosome c : population) {
			for (Chromosome cc : c.dominating) {
				cc.n++;
			}
		}
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

	public void crowdingSort(List<Chromosome> front) {
		int objectivesNumber = moop.getNumberOfObjectives();
		double inf = Double.MAX_VALUE / moop.getNumberOfObjectives();
		for (int i = 0; i < objectivesNumber; i++) {
			Collections.sort(front, new ObjectiveFunctionComparator(i));
			front.forEach(c -> c.setCrowdingDistance(0));
			front.get(0).setCrowdingDistance(front.get(0).getCrowdingDistance() + inf);
			int lastIndex = front.size() - 1;
			front.get(lastIndex).setCrowdingDistance(front.get(lastIndex).getCrowdingDistance() + inf);

			for (int j = 1; j < front.size() - 1; j++) {
				Chromosome c = front.get(j);
				Chromosome left = front.get(j - 1);
				Chromosome right = front.get(j + 1);
				c.setCrowdingDistance(
						c.getCrowdingDistance() + (right.objective[i] - left.objective[i]) / (fmax[i] - fmin[i]));
			}
		}

		Collections.sort(front, csComparator);
		Collections.reverse(front);
	}

	/**
	 * Evaluates all chromosomes in a population.
	 */
	public void evaluatePopulation(List<Chromosome> population) {
		for (Chromosome c : population) {
			moop.evaluateSolution(c.solution, c.objective);
		}
	}
}
