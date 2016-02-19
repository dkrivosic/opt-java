package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class Move extends Action {
	
	public Move() {
		super(ActionType.Move);
		this.children = null;
	}
	
	@Override
	public void execute(Ant ant) {
		ant.move();
	}

	@Override
	public String toString() {
		return "Move";
	}
}
