package hr.fer.zemris.trisat;

import java.util.Iterator;

/**
 * Generator of assignment's neighbours.
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	private BitVector assignment;

	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
	}

	/**
	 * Returns iterator which generates new neighbour every time
	 * <code>next()</code> is called.
	 */
	@Override
	public Iterator<MutableBitVector> iterator() {
		Iterator<MutableBitVector> it = new Iterator<MutableBitVector>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return currentIndex < assignment.getSize();
			}

			@Override
			public MutableBitVector next() {
				MutableBitVector next = assignment.copy();
				next.set(currentIndex, !assignment.get(currentIndex));
				currentIndex++;
				return next;
			}
		};
		return it;
	}

	/**
	 * Generates all neighbours and caches them. If this method was already
	 * called, it will return cached array of neighbours.
	 */
	public MutableBitVector[] createNeighbourhood() {
		int n = assignment.getSize();
		MutableBitVector neighbours[] = new MutableBitVector[n];
		for (int i = 0; i < n; i++) {
			MutableBitVector tmp = assignment.copy();
			tmp.set(i, !tmp.get(i));
			neighbours[i] = tmp;
		}
		return neighbours;
	}

}
