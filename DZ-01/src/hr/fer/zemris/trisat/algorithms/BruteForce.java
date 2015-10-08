package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Brute force algorithm that checks every possible combination of variable
 * values.
 */
public class BruteForce implements Algorithm {
	private SATFormula formula;

	public BruteForce(SATFormula formula) {
		this.formula = formula;
	}

	@Override
	public void run() {
		int vars = formula.getNumberOFVariables();
		int n = 1 << vars;
		BitVector combinations[] = new BitVector[n];

		// Generating all numbers between 0 and 2^vars
		for (int i = 0; i < n; i++) {
			String binary = Integer.toBinaryString(i);
			int diff = Math.abs(vars - binary.length());
			MutableBitVector vector = new MutableBitVector(vars);
			for (int j = 0; j < vars; j++) {
				if (j - diff >= 0 && j - diff < binary.length()) {
					if (binary.charAt(j - diff) == '1') {
						vector.set(j, true);
					} else {
						vector.set(j, false);
					}
				}
			}
			combinations[i] = vector;
		}

		// Checking every combination
		for (int i = 0; i < n; i++) {
			if (formula.isSatisfied(combinations[i])) {
				System.out.println(combinations[i]);
			}
		}
	}

}
