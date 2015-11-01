package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Operators {
	private Random random;

	private double p;

	public Operators(double p) {
		this.p = p;
		this.random = new Random();
	}

	/**
	 * Inserts given sticks into chromosome on a first available space in
	 * descending order (by size).
	 */
	private void insertDescending(Chromosome c, int[] stickIndexes) {
		stickIndexes = sortSticks(c, stickIndexes);

		int n = stickIndexes.length;
		for (int i = 0; i < n; i++) {
			boolean inserted = false;
			int m = c.places.length;
			Stick s = c.sticks[stickIndexes[i]];
			for (int j = 0; j < m; j++) {
				if (c.places[j].size + s.size <= c.places[j].capacity) {
					inserted = true;
					c.addStick(s.index, j);
					break;
				}
			}
			if (!inserted) {
				c.addPlace(m, new int[] { s.index });
			}
		}
	}

	/**
	 * Sorts sticks in descending order by size and returns sticks indices.
	 */
	private int[] sortSticks(Chromosome c, int[] stickIndexes) {
		List<Stick> sticksTmp = new ArrayList<>();
		for (int index : stickIndexes) {
			sticksTmp.add(c.sticks[index]);
		}
		Collections.sort(sticksTmp, Collections.reverseOrder());
		for (int i = 0; i < sticksTmp.size(); i++) {
			stickIndexes[i] = sticksTmp.get(i).index;
		}
		return stickIndexes;
	}

	/**
	 * Mutates the chromosome.
	 */
	public void mutate(Chromosome c) {
		Random random = new Random();
		int n = c.places.length;
		int[] removed = new int[0];
		for (int i = 0; i < n; i++) {
			if (random.nextDouble() < p) {
				int[] rest = c.removePlace(random.nextInt(c.places.length));
				removed = concat(removed, rest);
			}
		}
		insertDescending(c, removed);
	}

	/**
	 * Concatenates two arrays.
	 */
	private int[] concat(int[] a, int[] b) {
		int aLen = a.length;
		int bLen = b.length;
		int[] c = new int[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	/**
	 * Crossover operator is applied to given parents.
	 * 
	 * @return child chromosome
	 */
	public Chromosome crossover(Chromosome firstParent, Chromosome secondParent) {
		Chromosome firstChild = firstParent.copy();
		Chromosome secondChild = secondParent.copy();

		int n = firstChild.places.length;
		int r1 = random.nextInt(n);
		int r2 = random.nextInt(n);
		int firstPoint = Math.min(r1, r2);
		int secondPoint = Math.max(r1, r2);
		int insertPoint = random.nextInt(secondChild.places.length);
		int diff = secondPoint - firstPoint;
		int[] unassigned = new int[0];
		for (int i = 0; i < diff; i++) {
			int[] removed = firstChild.removePlace(firstPoint);
			for (int index : removed) {
				for (int j = 0; j < secondChild.places.length; j++) {
					if (secondChild.containsStick(j, index)) {
						int[] tmp = secondChild.removePlace(j);
						unassigned = concat(unassigned, tmp);
					}
				}
			}
			secondChild.addPlace(insertPoint, removed);
		}

		List<Integer> unassignedList = new ArrayList<>();

		for (int i = 0; i < unassigned.length; i++) {
			if (!secondChild.containsStick(unassigned[i])) {
				unassignedList.add(unassigned[i]);
			}
		}

		n = unassignedList.size();
		unassigned = new int[n];
		for (int i = 0; i < n; i++) {
			unassigned[i] = unassignedList.get(i);
		}

		insertDescending(secondChild, unassigned);

		return secondChild;
	}

}
