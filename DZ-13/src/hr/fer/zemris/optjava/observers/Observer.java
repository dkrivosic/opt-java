package hr.fer.zemris.optjava.observers;

import hr.fer.zemris.optjava.gp.actions.ActionType;

public interface Observer {
	public void update(ActionType type);
}
