package hr.fer.zemris.optjava.dz4.part2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chromosome implements Comparable<Chromosome> {
	public Stick[] sticks;
	public Place[] places;
	public double fitness;
	private final int placeCapacity;

	public Chromosome(Stick[] sticks, Place[] places) {
		this.sticks = Arrays.copyOf(sticks, sticks.length);
		this.places = Arrays.copyOf(places, places.length);
		this.fitness = 0;
		this.placeCapacity = 20;
	}

	public Chromosome(Stick[] sticks) {
		this.sticks = sticks;
		this.places = new Place[0];
		this.fitness = 0;
		this.placeCapacity = 10;
	}

	/**
	 * Adds stick <code>stickIndex</code> to <code>placeIndex</code>-th place in
	 * a container.
	 */
	public void addStick(int stickIndex, int placeIndex) {
		Place p = places[placeIndex];
		Stick s = sticks[stickIndex];

		if (p.sticksCount == 0) {
			p.sticksCount++;
			p.someStick = s.index;
			s.place = p.index;
		} else if (p.sticksCount == 1) {
			p.sticksCount++;
			Stick someStick = sticks[p.someStick];
			someStick.next = s.index;
			someStick.previous = s.index;
			s.next = someStick.index;
			s.previous = someStick.index;
			s.place = p.index;
		} else {
			p.sticksCount++;
			Stick someStick = sticks[p.someStick];
			Stick previous = sticks[someStick.previous];
			s.next = someStick.index;
			someStick.previous = s.index;
			previous.next = s.index;
			s.previous = previous.index;
			s.place = p.index;
		}
		p.size += s.size;
	}

	/**
	 * Removes stick <code>stickIndex</code> from <code>placeIndex</code>-th
	 * place in a container.
	 */
	public void removeStick(int stickIndex, int placeIndex) {
		Stick s = sticks[stickIndex];
		Place p = places[placeIndex];
		if (p.sticksCount == 1) {
			s.place = -1;
			p.someStick = -1;
		} else if (p.sticksCount == 2) {
			s.place = -1;
			Stick other = sticks[s.next];
			other.next = -1;
			other.previous = -1;
			s.next = -1;
			s.previous = -1;
			if (p.someStick == s.index) {
				p.someStick = other.index;
			}
		} else {
			sticks[s.previous].next = s.next;
			sticks[s.next].previous = s.previous;
			if (p.someStick == s.index) {
				p.someStick = s.next;
			}
			s.place = -1;
			s.next = -1;
			s.previous = -1;
		}
		p.sticksCount--;
		p.size -= s.size;
	}

	/**
	 * Moves stick with index <code>stickIndex</code> from one place to another.
	 */
	public void moveStick(int stickIndex, int from, int to) {
		removeStick(stickIndex, from);
		addStick(stickIndex, to);
	}

	/**
	 * Checks if there is stick <code>stickIndex</code> at
	 * <code>placeIndex</code>-th place in a container.
	 */
	public boolean containsStick(int placeIndex, int stickIndex) {
		Place p = places[placeIndex];
		if (p.sticksCount == 0) {
			return false;
		}

		int nextStick = p.someStick;
		for (int i = 0; i < p.sticksCount; i++) {
			if (nextStick == stickIndex) {
				return true;
			}
			nextStick = sticks[nextStick].next;
		}
		return false;
	}

	/**
	 * Checks if Chromosome contains stick with index <code>stickIndex</code>.
	 */
	public boolean containsStick(int stickIndex) {
		for (int i = 0; i < places.length; i++) {
			if (containsStick(i, stickIndex)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return copy of current chromosome
	 */
	public Chromosome copy() {
		Stick[] csticks = new Stick[sticks.length];
		Place[] cplaces = new Place[places.length];
		for (int i = 0; i < sticks.length; i++) {
			csticks[i] = sticks[i].copy();
		}
		for (int i = 0; i < places.length; i++) {
			cplaces[i] = places[i].copy();
		}
		return new Chromosome(csticks, cplaces);
	}

	/**
	 * Randomly rearranges sticks in a container (chromosome).
	 */
	public void randomize() {
		for (int i = 0; i < sticks.length; i++)
			sticks[i].index = i;

		List<Stick> ls = Arrays.asList(Arrays.copyOf(sticks, sticks.length));
		Collections.shuffle(ls);

		for (Stick s : sticks) {
			boolean inserted = false;
			for (Place p : places) {
				if (p.size + s.size <= p.capacity) {
					addStick(s.index, p.index);
					inserted = true;
					break;
				}
			}

			if (!inserted) {
				int n = places.length;
				places = Arrays.copyOf(places, n + 1);
				Place np = new Place(n, placeCapacity);
				places[n] = np;
				addStick(s.index, np.index);
			}
		}
	}

	/**
	 * Returns sum of the stick sizes in a place with index
	 * <code>placeIndex</code>.
	 */
	public int placeSize(int placeIndex) {
		int next = places[placeIndex].someStick;
		int ret = 0;
		for (int i = 0; i < places[placeIndex].sticksCount; i++) {
			ret += sticks[next].size;
			next = sticks[next].next;
		}
		return ret;
	}

	/**
	 * Removes place from a chromosome and returns sticks that were on that
	 * place.
	 */
	public int[] removePlace(int placeIndex) {
		int n = places[placeIndex].sticksCount;
		int[] removed = new int[n];
		int next = places[placeIndex].someStick;
		for (int i = 0; i < n; i++) {
			Stick s = sticks[next];
			removed[i] = s.index;
			next = s.next;
		}
		int m = places.length - 1;
		Place[] placesChanged = new Place[m];
		for (int i = 0; i < placeIndex; i++) {
			placesChanged[i] = places[i];
		}

		for (int i = placeIndex; i < m; i++) {
			placesChanged[i] = places[i + 1];
			placesChanged[i].index--;
			int nextStick = sticks[placesChanged[i].someStick].index;
			for (int j = 0; j < placesChanged[i].sticksCount; j++) {
				if (nextStick > -1) {
					sticks[nextStick].place--;
					nextStick = sticks[nextStick].next;
				}
			}
		}
		places = placesChanged;
		return removed;
	}

	/**
	 * Adds a new place to index <code>index</code> and fills it with given
	 * sticks.
	 */
	public void addPlace(int index, int[] stickIndexes) {
		Place[] newPlaces = new Place[places.length + 1];

		while (index >= places.length)
			index--;

		for (int i = 0; i < index; i++) {
			newPlaces[i] = places[i];
		}
		newPlaces[index] = new Place(index, placeCapacity);
		for (int i = index; i < places.length; i++) {
			newPlaces[i + 1] = places[i];
			newPlaces[i + 1].index++;
		}
		places = newPlaces;

		for (int i : stickIndexes) {
			addStick(i, index);
		}
	}

	@Override
	public String toString() {
		String str = "| ";
		for (int i = 0; i < places.length; i++) {
			int nextStick = places[i].someStick;
			for (int j = 0; j < places[i].sticksCount; j++) {
				Stick s = sticks[nextStick];
				str += s.size + " ";
				nextStick = s.next;
			}
			str += "| ";
		}
		return str;
	}

	@Override
	public int compareTo(Chromosome o) {
		if (this.fitness == o.fitness) {
			return 0;
		} else if (this.fitness < o.fitness) {
			return -1;
		}
		return 1;
	}

}
