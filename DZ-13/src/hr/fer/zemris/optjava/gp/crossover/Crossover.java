package hr.fer.zemris.optjava.gp.crossover;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import hr.fer.zemris.optjava.gp.actions.Action;
import hr.fer.zemris.optjava.gp.ant.Ant;

public class Crossover implements ICrossover {
	private Random random;

	public Crossover(Random random) {
		super();
		this.random = random;
	}

	@Override
	public List<Ant> crossover(Ant firstParent, Ant secondParent) {
		int n = firstParent.getNumberOfNodes();
		int counter = random.nextInt(n - 1) + 1;
		int childIndex1 = 0;
		Action parentNode1 = null;
		Queue<Action> q = new LinkedList<>();
		q.add(firstParent.root);

		// Find subtree root for first parent
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
					childIndex1 = j;
					parentNode1 = current;
					found = true;
				}
			}
		}

		n = secondParent.getNumberOfNodes();
		counter = random.nextInt(n - 1) + 1;
		int childIndex2 = 0;
		Action parentNode2 = null;
		q = new LinkedList<>();
		q.add(secondParent.root);
		// Find subtree root for second parent
		found = false;
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
					childIndex2 = j;
					parentNode2 = current;
					found = true;
				}
			}
		}

		Action tmp = parentNode1.children[childIndex1];
		parentNode1.children[childIndex1] = parentNode2.children[childIndex2];
		parentNode2.children[childIndex2] = tmp;

		return Arrays.asList(new Ant[] { firstParent, secondParent });

	}
}
