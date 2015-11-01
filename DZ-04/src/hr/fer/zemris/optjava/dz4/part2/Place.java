package hr.fer.zemris.optjava.dz4.part2;

/**
 * One place in a container (box).
 */
public class Place {
	public int someStick;
	public int sticksCount;
	public int index;
	public int capacity;
	public int size;

	public Place(int index, int capacity) {
		this.index = index;
		this.someStick = -1;
		this.sticksCount = 0;
		this.capacity = capacity;
		this.size = 0;
	}

	/**
	 * @return copy of current place
	 */
	public Place copy() {
		Place c = new Place(this.index, this.capacity);
		c.someStick = this.someStick;
		c.sticksCount = this.sticksCount;
		c.size = this.size;
		return c;
	}
}
