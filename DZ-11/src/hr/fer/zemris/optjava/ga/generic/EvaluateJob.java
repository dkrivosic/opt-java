package hr.fer.zemris.optjava.ga.generic;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Job for worker thread. It repeatedly takes solution from
 * <code>nonEvaluated</code> queue, evaluates it and puts it in the
 * <code>evaluated</code> queue. Run method finishes when it gets solution with
 * null data from the <code>nonEvaluated</code> queue.
 */
public class EvaluateJob<T> implements Runnable {
	private LinkedBlockingQueue<GASolution<T>> nonEvaluated;
	private LinkedBlockingQueue<GASolution<T>> evaluated;
	private IGAEvaluator<T> evaluator;

	/**
	 * @param nonEvaluated
	 *            Queue with non evaluated solutions.
	 * @param evaluated
	 *            Queue with evaluated solutions.
	 * @param evaluator
	 *            Solution evaluator.
	 */
	public EvaluateJob(LinkedBlockingQueue<GASolution<T>> nonEvaluated, LinkedBlockingQueue<GASolution<T>> evaluated,
			IGAEvaluator<T> evaluator) {
		super();
		this.nonEvaluated = nonEvaluated;
		this.evaluated = evaluated;
		this.evaluator = evaluator;
	}

	@Override
	public void run() {
		while (true) {
			GASolution<T> solution = null;
			try {
				solution = nonEvaluated.take();
			} catch (InterruptedException e1) {
				System.err.println("Interrupted");
				System.exit(1);
			}
			// Poison pill
			if (solution.getData() == null) {
				break;
			}

			evaluator.evaluate(solution);
			try {
				evaluated.put(solution);
			} catch (InterruptedException e) {
				System.err.println("Interrupted while waiting");
				break;
			}
		}
	}

}
