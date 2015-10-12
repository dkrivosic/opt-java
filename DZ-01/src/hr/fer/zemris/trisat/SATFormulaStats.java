package hr.fer.zemris.trisat;

/**
 * Statistics for SAT formula.
 */
public class SATFormulaStats {
	private final double percentageConstantUp = 0.01;
	private final double percentageConstantDown = 0.1;
	private final double percentageUnitAmount = 50;
	private SATFormula formula;
	private int numberOfSatisfied;
	private double post[];
	private BitVector assignment;
	private double percentageBonus;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		numberOfSatisfied = 0;
		int n = formula.getNumberOfClauses();
		post = new double[n];
		for (int i = 0; i < n; i++) {
			post[i] = 0.0;
		}
	}

	/**
	 * Saves assignment and updates number of satisfied clauses and
	 * percentageBonus. If <code>updatePercentages</code> flag is set, it also
	 * updates statistics for each variable.
	 */
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		this.assignment = assignment;
		this.numberOfSatisfied = 0;
		this.percentageBonus = 0;
		int n = formula.getNumberOfClauses();
		for (int i = 0; i < n; i++) {
			boolean satisfied = formula.getClause(i).isSatisfied(assignment);
			if (satisfied && updatePercentages) {
				numberOfSatisfied++;
				post[i] += (1 - post[i]) * percentageConstantUp;
				percentageBonus += percentageUnitAmount * (1 - post[i]);
			} else if (!satisfied && updatePercentages) {
				post[i] += (0 - post[i]) * percentageConstantDown;
				percentageBonus -= percentageUnitAmount * (1 - post[i]);
			} else if (satisfied) {
				numberOfSatisfied++;
				percentageBonus += percentageUnitAmount * (1 - post[i]);
			} else if (!satisfied) {
				percentageBonus -= percentageUnitAmount * (1 - post[i]);
			}
		}
	}

	/**
	 * @return number of satisfied clauses by last saved assignment.
	 */
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	/**
	 * @return <code>true</code> if last saved assignment satisfies formula,
	 *         <code>false</code> otherwise.
	 */
	public boolean isSatisfied() {
		return formula.isSatisfied(assignment);
	}

	/**
	 * @return percentage bonus calculated for last saved assignment.
	 */
	public double getPercentageBonus() {
		return percentageBonus;
	}

	/**
	 * @return percentage for <code>index</code>-th clause.
	 */
	public double getPercentage(int index) {
		return post[index];
	}
}
