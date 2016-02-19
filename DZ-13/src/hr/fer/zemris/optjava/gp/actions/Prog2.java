package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class Prog2 extends Action {
	public Prog2() {
		super(ActionType.Prog2);
		this.children = new Action[2];
	}

	public Prog2(Action action1, Action action2) {
		super(ActionType.Prog2);
		this.children = new Action[2];
		this.children[0] = action1;
		this.children[1] = action2;
	}

	@Override
	public void execute(Ant ant) {
		children[0].execute(ant);
		children[1].execute(ant);
	}

	@Override
	public String toString() {
		return "Prog2(" + children[0].toString() + ", " + children[1].toString() + ")";
	}

}
