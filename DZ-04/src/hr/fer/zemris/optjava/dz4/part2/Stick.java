package hr.fer.zemris.optjava.dz4.part2;

public class Stick implements Comparable<Stick> {
	public int previous;
	public int next;
	public int index;
	public int place;
	public int size;

	public Stick(int index, int size) {
		this.index = index;
		this.size = size;
		this.previous = -1;
		this.next = -1;
		this.place = -1;
	}

	/**
	 * @return copy of current stick
	 */
	public Stick copy() {
		Stick c = new Stick(this.index, this.size);
		c.next = this.next;
		c.previous = this.previous;
		c.place = this.place;
		return c;
	}

	@Override
	public int compareTo(Stick o) {
		return this.size - o.size;
	}
	
	@Override
	public String toString() {
		return String.valueOf(size);
	}
}
