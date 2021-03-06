package hr.fer.zemris.parser;

import hr.fer.zemris.trisat.Clause;
import hr.fer.zemris.trisat.SATFormula;

/**
 * Used for parsing input file.
 */
public class Parser {
	private int numberOfVariables;
	private int numberOfClauses;
	private Clause clauses[];
	private int index;

	public Parser() {
		this.index = 0;
	}

	/**
	 * Parses one line of input file. It ignores comments and empty lines.
	 * Clauses are saved to <code>clauses</code> array.
	 */
	public void parseLine(String str) {
		str = str.trim();
		if (str.isEmpty() || str.startsWith("c"))
			return;
		if (str.startsWith("p")) {
			String[] tmp = str.split("( )+");
			numberOfVariables = Integer.parseInt(tmp[2]);
			numberOfClauses = Integer.parseInt(tmp[3]);
			clauses = new Clause[numberOfClauses];
		} else {
			String tmp[] = str.split(" ");
			int n = tmp.length - 1; // tmp ends with 0
			int[] indexes = new int[n];
			for (int i = 0; i < n; i++) {
				indexes[i] = Integer.parseInt(tmp[i]);
			}
			Clause clause = new Clause(indexes);
			clauses[index] = clause;
			index++;
		}
	}

	/**
	 * Generates <code>SATFormula</code> from parsed lines.
	 * 
	 * @return generated formula.
	 */
	public SATFormula generateFormula() {
		return new SATFormula(numberOfVariables, clauses);
	}
}
