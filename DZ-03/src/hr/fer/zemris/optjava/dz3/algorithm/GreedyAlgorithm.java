package hr.fer.zemris.optjava.dz3.algorithm;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbourhood.INeighbourhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Greedy algorithm for minimizing or maximizing function.
 */
public class GreedyAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm<SingleObjectiveSolution> {

	private final int MAX_ITER = 10000;

	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;

	public GreedyAlgorithm(IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function,
			boolean minimize) {
		super();
		this.decoder = decoder;
		this.neighbourhood = neighbourhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
	}

	@Override
	public T run() {
		@SuppressWarnings("unchecked")
		T solution = (T) startWith.newLikeThis();
		solution.setValue(function.valueAt(decoder.decode(startWith)), minimize);

		int i = 0;
		do {
			i++;
			T neighbour = neighbourhood.randomNeighbour(solution);
			neighbour.setValue(function.valueAt(decoder.decode(neighbour)), minimize);
			if (neighbour.getFitness() > solution.getFitness()) {
				solution = neighbour;
			}

		} while (i < MAX_ITER);

		return solution;
	}

}
