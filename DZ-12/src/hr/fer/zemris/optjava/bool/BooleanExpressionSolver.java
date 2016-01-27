package hr.fer.zemris.optjava.bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class BooleanExpressionSolver {
	private String expr;
	private Map<String, Integer> map;
	private List<String> variables;

	public BooleanExpressionSolver(String expr) {
		super();
		this.expr = expr;
		this.map = new HashMap<>();
		this.variables = parseVariables(expr);
	}

	/**
	 * Evaluates boolean expression with given variable values.
	 * 
	 * @param values
	 *            variable values
	 * @return expression value
	 */
	public int getValue(int[] values) {
		setValues(values);
		String currentExpr = expr;
		currentExpr = evaluateVariables(currentExpr);

		while (!currentExpr.matches("[01]")) {
			currentExpr = applyNegations(currentExpr);
			currentExpr = solveAnd(currentExpr);
			currentExpr = solveOr(currentExpr);
			currentExpr = removeParentheses(currentExpr);
		}

		return Integer.parseInt(currentExpr);
	}

	/**
	 * Returns expression values for all combinations of variable values.
	 */
	public Integer[] getAllValues() {
		int[][] values = binaryGenerator(variables.size());
		int n = values.length;
		Integer[] solutions = new Integer[n];

		for (int i = 0; i < n; i++) {
			solutions[i] = getValue(values[i]);
		}

		return solutions;
	}

	public List<String> getVariables() {
		return variables;
	}

	public int getNumberOfVariables() {
		return variables.size();
	}

	private List<String> parseVariables(String expr) {
		expr = expr.replaceAll("(AND)|(NOT)|(OR)|[\\(\\)]", " ");
		expr = expr.replaceAll("[ ]+", " ").trim();
		return new ArrayList<>(new TreeSet<>(Arrays.asList(expr.split(" "))));
	}

	/**
	 * Sets the variable values to given values.
	 */
	private void setValues(int[] values) {
		for (int i = 0; i < variables.size(); i++) {
			map.put(variables.get(i), values[i]);
		}
	}

	/**
	 * Changes values with their values in the expression and returns new
	 * expression.
	 * 
	 * @param expr
	 *            expression
	 * @return new expression
	 */
	private String evaluateVariables(String expr) {
		for (String v : map.keySet()) {
			expr = expr.replaceAll(v + "(?![a-zA-Z]+)", map.get(v).toString());
		}
		return expr;
	}

	/**
	 * Applies negations where possible. Changes 0 to 1 and 1 to 0 where NOT 0
	 * or NOT 1 appears in the expression.
	 * 
	 * @param expr
	 *            expression
	 * @return new expression
	 */
	private String applyNegations(String expr) {
		expr = expr.replaceAll("(NOT)[ ]+0", "1");
		expr = expr.replaceAll("(NOT)[ ]+1", "0");
		return expr;
	}

	/**
	 * Solves AND operation where both operands are numbers (not expressions).
	 * 
	 * @param expr
	 *            expression
	 * @return new expression
	 */
	private String solveAnd(String expr) {
		expr = expr.replaceAll("0[ ]+AND[ ]+0", "0");
		expr = expr.replaceAll("0[ ]+AND[ ]+1", "0");
		expr = expr.replaceAll("1[ ]+AND[ ]+0", "0");
		expr = expr.replaceAll("1[ ]+AND[ ]+1", "1");
		return expr;
	}

	/**
	 * Solves AND operation where both operands are numbers (not expressions).
	 * 
	 * @param expr
	 *            expression
	 * @return new expression
	 */
	private String solveOr(String expr) {
		expr = expr.replaceAll("0[ ]+OR[ ]+0", "0");
		expr = expr.replaceAll("0[ ]+OR[ ]+1", "1");
		expr = expr.replaceAll("1[ ]+OR[ ]+0", "1");
		expr = expr.replaceAll("1[ ]+OR[ ]+1", "1");
		return expr;
	}

	/**
	 * Removes parentheses where not necessary.
	 * 
	 * @param expr
	 *            expression
	 * @return new expression
	 */
	private String removeParentheses(String expr) {
		expr = expr.replaceAll("[\\(][ ]*1[ ]*[\\)]", "1");
		expr = expr.replaceAll("[\\(][ ]*0[ ]*[\\)]", "0");
		return expr;
	}

	public static int[][] binaryGenerator(int n) {
		int combinations = (int) Math.pow(2, n);
		int[][] ret = new int[combinations][n];

		for (int i = 0; i < combinations; i++) {
			String bin = Integer.toBinaryString(i);
			int len = bin.length();
			for (int j = 0; j < (n - len); j++) {
				bin = "0" + bin;
			}

			for (int j = 0; j < n; j++) {
				ret[i][j] = Integer.parseInt(String.valueOf(bin.charAt(j)));
			}
		}

		return ret;
	}

}
