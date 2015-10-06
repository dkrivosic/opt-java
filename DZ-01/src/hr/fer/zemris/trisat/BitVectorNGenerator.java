package hr.fer.zemris.trisat;

import java.util.Iterator;

/**
 * Generator of assignment's neighbours.
 */
public class BitVectorNGenerator implements Iterable<MutableBitVector> {
	private BitVector assignment;
	private MutableBitVector[] neighbours;

	public BitVectorNGenerator(BitVector assignment) {
		this.assignment = assignment;
		neighbours = createNeighbourhood();
	}

	@Override
	public Iterator<MutableBitVector> iterator() {
		Iterator<MutableBitVector> it = new Iterator<MutableBitVector>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return neighbours != null && currentIndex < neighbours.length;
			}

			@Override
			public MutableBitVector next() {
				return neighbours[currentIndex++];
			}
		};
		return it;
	}

	/**
	 * Generates all neighbours and caches them. If this method was already
	 * called, it will return cached array of neighbours.
	 */
	public MutableBitVector[] createNeighbourhood() {
		if (neighbours != null) {
			return neighbours;
		}

		int n = assignment.getSize();
		neighbours = new MutableBitVector[n];
		for (int i = 0; i < n; i++) {
			MutableBitVector tmp = assignment.copy();
			tmp.set(i, !tmp.get(i));
			neighbours[i] = tmp;
		}
		return neighbours;
	}

}
