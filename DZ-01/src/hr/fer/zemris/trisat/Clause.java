package hr.fer.zemris.trisat;

/**
 * One clause of formula.
 */
public class Clause {

	private int literals[];

	public Clause(int[] indexes) {
		this.literals = indexes;
	}

	/**
	 * @return number of literals in a clause.
	 */
	public int getSize() {
		return literals.length;
	}

	/**
	 * @return <code>index</code>-th literal in this clause.
	 */
	public int getLiteral(int index) {
		// Uz pretpostavku da su komplementi negativni
		return Math.abs(literals[index]);
	}

	/**
	 * Checks if this clause is satisfied by given variables
	 * <code>assignment</code>.
	 */
	public boolean isSatisfied(BitVector assignment) {
		boolean satisfied = false;
		for (int literal : literals) {
			int index = Math.abs(literal) - 1;
			if (literal >= 0) {
				satisfied = satisfied || assignment.get(index);
			} else {
				satisfied = satisfied || !assignment.get(index);
			}
		}
		return satisfied;
	}

	@Override
	public String toString() {
		String str = "( ";
		for (int literal : literals) {
			str += literal + " ";
		}
		str += ")";
		return str;
	}

}
