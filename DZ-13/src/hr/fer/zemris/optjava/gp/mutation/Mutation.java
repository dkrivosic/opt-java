package hr.fer.zemris.optjava.gp.mutation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import hr.fer.zemris.optjava.gp.actions.Action;
import hr.fer.zemris.optjava.gp.ant.Ant;
import hr.fer.zemris.optjava.gp.helpers.TreeGenerator;

public class Mutation implements IMutation {
	private TreeGenerator treeGen;
	private Random random;
	private int maxDepth;

	public Mutation(TreeGenerator treeGen, Random random, int maxDepth) {
		super();
		this.treeGen = treeGen;
		this.random = random;
		this.maxDepth = maxDepth;
	}

	public void mutate(Ant ant) {
		int n = ant.getNumberOfNodes();
		int counter = random.nextInt(n - 1) + 1;
		int childIndex = 0;
		Action parent = null;
		Queue<Action> q = new LinkedList<>();
		q.add(ant.root);

		// Find subtree root
		boolean found = false;
		while (!q.isEmpty() && !found) {
			Action current = q.poll();
			for (Action a : current.children) {
				if (a.children != null) {
					q.add(a);
				}
			}

			for (int j = 0; j < current.children.length; j++) {
				counter--;
				if (counter == 0) {
					childIndex = j;
					parent = current;
					found = true;
				}
			}
		}

		// Replace subtree with random generated subtree
		int subtreeDepth = random.nextInt(maxDepth - parent.depth) + 1;
		if (subtreeDepth == 1) {
			parent.children[childIndex] = treeGen.getRandomTerminal();
		} else {
			parent.children[childIndex] = treeGen.generateGrow(subtreeDepth);
		}
	}
}
