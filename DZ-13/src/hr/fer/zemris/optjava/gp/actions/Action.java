package hr.fer.zemris.optjava.gp.actions;

import hr.fer.zemris.optjava.gp.ant.Ant;
import hr.fer.zemris.optjava.observers.Observer;

public abstract class Action {
	public final ActionType type;
	public int depth;
	public Action[] children;

	public Action(ActionType type) {
		this.type = type;
	}

	public abstract void execute(Ant ant);
}
