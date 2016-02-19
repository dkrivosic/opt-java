package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class Left extends Action {
	
	public Left() {
		super(ActionType.Left);
		this.children = null;
	}

	@Override
	public void execute(Ant ant) {
		ant.turnLeft();
	}

	@Override
	public String toString() {
		return "Left";
	}
}
