package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.List;

public class Ant {

	private List<Integer> route;
	private double cost;

	public Ant() {
		route = new ArrayList<>();
	}

	/**
	 * Ant visits city with index <code>cityIndex</code> (city is added to ant's
	 * route).
	 */
	public void visit(int cityIndex) {
		route.add(cityIndex);
	}

	/**
	 * Checks if ant already visited city with index <code>cityIndex</code>.
	 */
	public boolean visited(int cityIndex) {
		return route.contains(cityIndex);
	}

	/**
	 * Takes a list of city indexes and checks if the ant has visited ALL of
	 * them.
	 */
	public boolean visitedAll(List<Integer> cities) {
		for (int city : cities) {
			if (!this.visited(city)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Calculates cost of ant's route using <code>function</code> and sets cost
	 * field.
	 */
	public void evaluate(CostFunction function) {
		this.cost = function.valueOf(route);
	}

	/**
	 * @return City index where ant begins its trip.
	 */
	public int getStartingCity() {
		return route.get(0);
	}

	/**
	 * @return total cost of ant's path.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return ant's route
	 */
	public List<Integer> getRoute() {
		return route;
	}

	@Override
	public String toString() {
		String str = "";
		int begin = route.indexOf(0);
		int current = begin;
		int n = route.size();
		for (int i = 0; i < n; i++) {
			str += (route.get(current) + 1) + "-";
			current = (current + 1) % n;
		}
		str += (route.get(begin) + 1);
		return str;
	}

}
