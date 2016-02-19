package hr.fer.zemris.optjava.gp.algorithm;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class AntEvaluator {
	private int maxMoves;
	private double plagiarismPunishment;

	public AntEvaluator(int maxMoves, double plagiarismPunishment) {
		this.maxMoves = maxMoves;
		this.plagiarismPunishment = plagiarismPunishment;
	}

	public void evaluate(Ant ant) {
		ant.reset();
		while (ant.getMoves() < maxMoves) {
			ant.root.execute(ant);
		}

		if (ant.getFoodEaten() == ant.getParentFoodEaten()) {
			ant.setFitness(plagiarismPunishment * ant.getFoodEaten());
		} else {
			ant.setFitness(ant.getFoodEaten());
		}
	}

	public int getMaxMoves() {
		return maxMoves;
	}

}
