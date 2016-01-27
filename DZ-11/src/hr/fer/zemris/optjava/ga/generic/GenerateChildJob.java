package hr.fer.zemris.optjava.ga.generic;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Job for worker thread. It repeatedly takes a number from <code>tasks</code>
 * queue, creates number of children equal to that number and puts them in
 * <code>children</code> queue.
 */
public class GenerateChildJob<T> implements Runnable {
	private LinkedBlockingQueue<Integer> tasks;
	private LinkedBlockingQueue<GASolution<T>> children;
	private IGAEvaluator<T> evaluator;
	private ISelection<T> selection;
	private ICrossover<T> crossover;
	private IMutation<T> mutation;
	private List<GASolution<T>> population;

	public GenerateChildJob(LinkedBlockingQueue<Integer> tasks, LinkedBlockingQueue<GASolution<T>> children,
			IGAEvaluator<T> evaluator, ISelection<T> selection, ICrossover<T> crossover, IMutation<T> mutation,
			List<GASolution<T>> population) {
		super();
		this.tasks = tasks;
		this.children = children;
		this.evaluator = evaluator;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.population = population;
	}

	@Override
	public void run() {
		while (true) {
			int n = 0;
			try {
				n = tasks.take();
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
				System.exit(1);
			}

			if (n == 0) {
				break;
			}

			for (int i = 0; i < n; i++) {
				GASolution<T> firstParent = selection.select(population);
				GASolution<T> secondParent = selection.select(population);
				GASolution<T> child = crossover.crossover(firstParent, secondParent);
				mutation.mutate(child);
				evaluator.evaluate(child);
				try {
					children.put(child);
				} catch (InterruptedException e) {
					System.err.println("Interrupted");
					System.exit(1);
				}
			}

		}
	}

}
