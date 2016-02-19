package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;

public class Prog3 extends Action {

	public Prog3() {
		super(ActionType.Prog3);
		this.children = new Action[3];
	}

	public Prog3(Action action1, Action action2, Action action3) {
		super(ActionType.Prog3);
		this.children = new Action[3];
		this.children[0] = action1;
		this.children[1] = action2;
		this.children[2] = action3;
	}

	@Override
	public void execute(Ant ant) {
		children[0].execute(ant);
		children[1].execute(ant);
		children[2].execute(ant);
	}

	@Override
	public String toString() {
		return "Prog3(" + children[0].toString() + ", " + children[1].toString() + ", " + children[2].toString() + ")";
	}
}
