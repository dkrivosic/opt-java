package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class Right extends Action {

	public Right() {
		super(ActionType.Right);
		this.children = null;
	}

	@Override
	public void execute(Ant ant) {
		ant.turnRight();
	}

	@Override
	public String toString() {
		return "Right";
	}
}
