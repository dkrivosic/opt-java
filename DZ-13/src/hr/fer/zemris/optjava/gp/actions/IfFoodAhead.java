package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class IfFoodAhead extends Action {

	public IfFoodAhead() {
		super(ActionType.IfFoodAhead);
		this.children = new Action[2];
	}

	public IfFoodAhead(Action action1, Action action2) {
		super(ActionType.IfFoodAhead);
		this.children = new Action[2];
		this.children[0] = action1;
		this.children[1] = action2;
	}

	@Override
	public void execute(Ant ant) {
		if (ant.isFoodAhead()) {
			children[0].execute(ant);
		} else {
			children[1].execute(ant);
		}

	}

	@Override
	public String toString() {
		return "IfFoodAhead(" + children[0].toString() + ", " + children[1].toString() + ")";
	}

}
