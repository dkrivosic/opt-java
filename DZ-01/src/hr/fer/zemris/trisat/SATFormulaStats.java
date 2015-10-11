package hr.fer.zemris.trisat;

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
		for(int i = 0; i < n; i++) {
			post[i] = 0.0;
		}
	}
	
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		this.assignment = assignment;
		this.numberOfSatisfied = 0;
		this.percentageBonus = 0;
		int n = formula.getNumberOfClauses();
		for(int i = 0; i < n; i++) {
			boolean satisfied = formula.getClause(i).isSatisfied(assignment);
			if(satisfied && updatePercentages) {
				numberOfSatisfied++;
				post[i] += (1 - post[i]) * percentageConstantUp;
				percentageBonus += percentageUnitAmount * (1 - post[i]);
			} else if(!satisfied && updatePercentages) {
				post[i] += (0 - post[i]) * percentageConstantDown;
				percentageBonus -= percentageUnitAmount * (1 - post[i]);
			} else if(satisfied) {
				numberOfSatisfied++;
				percentageBonus += percentageUnitAmount * (1 - post[i]);
			} else if(!satisfied) {
				percentageBonus -= percentageUnitAmount * (1 - post[i]);
			}
		}
	}
	
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}
	
	public boolean isSatisfied() {
		return formula.isSatisfied(assignment);
	}
	
	public double getPercentageBonus() {
		return percentageBonus;
	}
	
	public double getPercentage(int index) {
		return post[index];
	}
}
