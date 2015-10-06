package hr.fer.zemris.trisat;

/**
 * Formula defined with one or more clauses.
 */
public class SATFormula {
	private int numberOfVariables;
	private Clause[] clauses;

	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.numberOfVariables = numberOfVariables;
		this.clauses = clauses;
	}

	/**
	 * @return number of variables in a formula.
	 */
	public int getNumberOFVariables() {
		return numberOfVariables;
	}

	/**
	 * @return number of clauses that define this formula.
	 */
	public int getNumberOfClauses() {
		return clauses.length;
	}

	/**
	 * @return clause at <code>index</code>-th place in <code>clauses</code>
	 *         array.
	 */
	public Clause getClause(int index) {
		return clauses[index];
	}

	/**
	 * Checks if given <code>assignment</code> satisfies this formula.
	 */
	public boolean isSatisfied(BitVector assignment) {
		boolean satisfied = true;
		for (Clause clause : clauses) {
			satisfied = satisfied && clause.isSatisfied(assignment);
		}
		return satisfied;
	}

	@Override
	public String toString() {
		String str = "";
		for (Clause clause : clauses) {
			str += clause.toString() + " ";
		}
		return str;
	}

}
