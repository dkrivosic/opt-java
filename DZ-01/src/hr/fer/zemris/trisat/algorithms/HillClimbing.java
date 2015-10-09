package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Hill climbing algorithm.
 */
public class HillClimbing implements Algorithm {
	private SATFormula formula;
	private final int MAX_ITER = 100000;

	public HillClimbing(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public void run() {
		Random rand = new Random(System.currentTimeMillis());
		BitVector x = new BitVector(rand, formula.getNumberOFVariables());
		BitVector xx;
		int iter = 0;
		do {
			MutableBitVector neighbours[] = new BitVectorNGenerator(x).createNeighbourhood();
			List<MutableBitVector> best = new ArrayList<>();
			int fit = evaluate(x);
			int bestFit = -1;
			for (MutableBitVector neighhbour : neighbours) { // Choosing the
																// best
																// neighbours
				int tmp = evaluate(neighhbour);
				if (tmp > bestFit) {
					best.clear();
					best.add(neighhbour);
					bestFit = tmp;
				} else if (tmp == fit) {
					best.add(neighhbour);
				}
			}

			if (bestFit < fit) {
				System.out.println("Stuck in local optimum");
				return;
			}

			// Pick random best neighbour
			xx = x;
			x = best.get(rand.nextInt(best.size()));
			if (x.equals(xx))
				break; // ???

			if (formula.isSatisfied(x)) {
				System.out.println("Solution: " + x);
				return;
			}

		} while (++iter < MAX_ITER);

		System.out.println("Solution not found in " + MAX_ITER + " iterations.");

	}

	/**
	 * Evaluates solution as number of satisfied clauses in a formula.
	 */
	private int evaluate(BitVector x) {
		int satisfied = 0;
		int n = formula.getNumberOfClauses();
		for (int i = 0; i < n; i++) {
			if (formula.getClause(i).isSatisfied(x)) {
				satisfied++;
			}
		}
		return satisfied;
	}

}
