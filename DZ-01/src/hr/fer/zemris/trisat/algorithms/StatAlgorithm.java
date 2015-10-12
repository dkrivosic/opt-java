package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.MutableBitVectorComp;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

/**
 * Algorithm that uses statistics to evaluate solution. Statistics are updated
 * in every iteration when new solution is chosen.
 */
public class StatAlgorithm implements Algorithm {
	private SATFormula formula;
	private final int numberOfBest = 2;
	private final int MAX_ITER = 100000;

	public StatAlgorithm(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public void run() {
		SATFormulaStats stats = new SATFormulaStats(formula);
		Random rand = new Random(System.currentTimeMillis());
		BitVector x = new BitVector(rand, formula.getNumberOFVariables());
		stats.setAssignment(x, true);
		List<BitVector> best = new ArrayList<>();
		int iter = 0;

		do {
			iter++;

			if (formula.isSatisfied(x)) {
				System.out.println("Solution: " + x);
				System.out.println("iterations: " + iter);
				return;
			}

			List<MutableBitVector> neighbours = Arrays.asList(new BitVectorNGenerator(x).createNeighbourhood());
			Collections.sort(neighbours, new MutableBitVectorComp(stats));
			best = new ArrayList<>(neighbours.subList(0, numberOfBest));
			x = best.get(rand.nextInt(best.size()));
			stats.setAssignment(x, true);

		} while (iter < MAX_ITER);

		System.out.println("Solution not found in " + MAX_ITER + " iterations.");
	}

}
