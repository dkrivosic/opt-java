package hr.fer.zemris.optjava.dz3.algorithm;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbourhood.INeighbourhood;
import hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution;

/**
 * Simulated annealing algorithm.
 */
public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<SingleObjectiveSolution> {

	private IDecoder<T> decoder;
	private INeighbourhood<T> neighbourhood;
	private T startWith;
	private IFunction function;
	private boolean minimize;
	private Random rand;
	private ITempSchedule tempSchedule;

	public SimulatedAnnealing(IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function,
			boolean minimize, ITempSchedule tempSchedule) {
		this.decoder = decoder;
		this.neighbourhood = neighbourhood;
		this.startWith = startWith;
		this.function = function;
		this.minimize = minimize;
		this.tempSchedule = tempSchedule;
		this.rand = new Random();
	}

	@Override
	public SingleObjectiveSolution run() {
		@SuppressWarnings("unchecked")
		T solution = (T) startWith.newLikeThis();
		solution.setValue(function.valueAt(decoder.decode(startWith)), minimize);
		double temp;
		final double a = 0.4; // Initial probability of accepting worse solution
		final double x = 0.9; // Probability reduction rate

		do {
			temp = tempSchedule.getNextTemperature();
			T neighbour = neighbourhood.randomNeighbour(solution);
			neighbour.setValue(function.valueAt(decoder.decode(neighbour)), minimize);

			if (neighbour.getFitness() >= solution.getFitness()) {
				solution = neighbour;
			} else {
				double probability = a * Math.pow(x, tempSchedule.getOuterLoopCounter() - 1);
				if (rand.nextDouble() < probability) {
					solution = neighbour;
				}
			}

		} while (temp > 0);

		return solution;
	}

}
