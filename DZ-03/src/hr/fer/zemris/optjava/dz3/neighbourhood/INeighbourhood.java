package hr.fer.zemris.optjava.dz3.neighbourhood;

public interface INeighbourhood<T> {

	/**
	 * @return Random neighbour of <code>solution</code>.
	 */
	public T randomNeighbour(T solution);
}
