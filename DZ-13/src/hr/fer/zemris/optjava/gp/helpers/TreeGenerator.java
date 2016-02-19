package hr.fer.zemris.optjava.gp.helpers;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import hr.fer.zemris.optjava.gp.actions.Action;
import hr.fer.zemris.optjava.gp.actions.ActionType;
import hr.fer.zemris.optjava.gp.actions.IfFoodAhead;
import hr.fer.zemris.optjava.gp.actions.Left;
import hr.fer.zemris.optjava.gp.actions.Move;
import hr.fer.zemris.optjava.gp.actions.Prog2;
import hr.fer.zemris.optjava.gp.actions.Prog3;
import hr.fer.zemris.optjava.gp.actions.Right;

/**
 * Ant generator that can generate Ant using full method or grow method.
 */
public class TreeGenerator {
	private static ActionType[] terminal = new ActionType[] { ActionType.Left, ActionType.Right, ActionType.Move };
	private static ActionType[] nonTerminal = new ActionType[] { ActionType.IfFoodAhead, ActionType.Prog2, ActionType.Prog3 };
	private Random random;

	public TreeGenerator() {
		this.random = new Random();
	}

	/**
	 * Generates new Action of given type.
	 * 
	 * @param type
	 *            action type
	 * @return new action
	 */
	public static Action generateAction(ActionType type) {
		if (type == ActionType.IfFoodAhead) {
			return new IfFoodAhead();
		} else if (type == ActionType.Prog2) {
			return new Prog2();
		} else if (type == ActionType.Prog3) {
			return new Prog3();
		} else if (type == ActionType.Left) {
			return new Left();
		} else if (type == ActionType.Right) {
			return new Right();
		} else {
			return new Move();
		}
	}

	/**
	 * @return random non terminal action
	 */
	private Action getRandomNonTerminal() {
		int i = random.nextInt(nonTerminal.length);
		return generateAction(nonTerminal[i]);
	}

	/**
	 * @return random terminal action
	 */
	public Action getRandomTerminal() {
		int i = random.nextInt(terminal.length);
		return generateAction(terminal[i]);
	}

	/**
	 * @return random terminal or non terminal action
	 */
	private Action getRandomAny() {
		int i = random.nextInt(terminal.length + nonTerminal.length);
		if (i < terminal.length) {
			return generateAction(terminal[i]);
		} else {
			return generateAction(nonTerminal[i - terminal.length]);
		}
	}

	/**
	 * Checks if action is terminal or non terminal.
	 * 
	 * @param type
	 *            action type
	 * @return <code>true</code> if given action type is terminal,
	 *         <code>false</code> otherwise.
	 */
	private boolean isTerminal(ActionType type) {
		for (ActionType t : terminal) {
			if (t == type) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generates new tree using full method.
	 * 
	 * @param maxDepth
	 *            tree depth
	 * @return root node
	 */
	public Action generateFull(int maxDepth) {
		int depth = random.nextInt(maxDepth - 1) + 2;
		Action root = getRandomNonTerminal();
		Queue<Action> q = new LinkedList<>();
		q.add(root);
		int d = 1;
		while (d < depth - 1) {
			int n = q.size();
			for (int i = 0; i < n; i++) {
				Action current = q.poll();
				current.depth = d;
				for (int j = 0; j < current.children.length; j++) {
					current.children[j] = getRandomNonTerminal();
					q.add(current.children[j]);
				}
			}
			d++;
		}

		int n = q.size();
		for (int i = 0; i < n; i++) {
			Action current = q.poll();
			current.depth = d;
			for (int j = 0; j < current.children.length; j++) {
				current.children[j] = getRandomTerminal();
				current.children[j].depth = depth;
			}
		}

		return root;

	}

	/**
	 * Generates new tree using grow method.
	 * 
	 * @param maxDepth
	 *            tree depth
	 * @return root node
	 */
	public Action generateGrow(int maxDepth) {
		int depth = random.nextInt(maxDepth - 1) + 2;
		Action root = getRandomNonTerminal();
		Queue<Action> q = new LinkedList<>();
		q.add(root);
		int d = 1;
		while (d < depth - 1) {
			int n = q.size();
			for (int i = 0; i < n; i++) {
				Action current = q.poll();
				current.depth = d;
				if (isTerminal(current.type)) {
					continue;
				}

				for (int j = 0; j < current.children.length; j++) {
					current.children[j] = getRandomAny();
					q.add(current.children[j]);
				}
			}
			d++;
		}

		int n = q.size();
		for (int i = 0; i < n; i++) {
			Action current = q.poll();
			current.depth = d;
			if (isTerminal(current.type)) {
				continue;
			}
			for (int j = 0; j < current.children.length; j++) {
				current.children[j] = getRandomTerminal();
				current.children[j].depth = depth;
			}
		}

		return root;
	}

}
