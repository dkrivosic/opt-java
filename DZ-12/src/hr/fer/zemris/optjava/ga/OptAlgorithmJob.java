package hr.fer.zemris.optjava.ga;

public class OptAlgorithmJob implements Runnable {
	private IOptAlgorithm algorithm;

	public OptAlgorithmJob(IOptAlgorithm algorithm) {
		super();
		this.algorithm = algorithm;
	}

	@Override
	public void run() {
		algorithm.run();
	}

	public void stopAlgorithm() {
		algorithm.finished = true;
	}

}
