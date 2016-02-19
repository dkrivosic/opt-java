package hr.fer.zemris.optjava.gp.ant;

import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.optjava.gp.actions.Action;
import hr.fer.zemris.optjava.gp.actions.ActionType;
import hr.fer.zemris.optjava.gp.helpers.TreeGenerator;
import hr.fer.zemris.optjava.gp.helpers.World;
import hr.fer.zemris.optjava.observers.Observer;

public class Ant {
	public int currentX;
	public int currentY;
	public Direction facing;
	public Action root;
	private int[][] map;
	private int parentFoodEaten;
	private int foodEaten;
	private double fitness;
	private Set<Observer> observers;
	private int maxMoves;
	private int moves;

	public Ant(Action root, int maxMoves) {
		this.maxMoves = maxMoves;
		observers = new HashSet<>();
		this.root = root;
		reset();
	}

	/**
	 * Ant turns to the left by 90 degrees.
	 */
	public void turnLeft() {
		if(moves >= maxMoves) {
			return;
		}
		moves++;
		
		if (facing == Direction.RIGHT) {
			facing = Direction.UP;
		} else if (facing == Direction.UP) {
			facing = Direction.LEFT;
		} else if (facing == Direction.LEFT) {
			facing = Direction.DOWN;
		} else {
			facing = Direction.RIGHT;
		}
		notifyObservers(ActionType.Left);
	}

	/**
	 * Ant turns to the right by 90 degrees.
	 */
	public void turnRight() {
		if(moves >= maxMoves) {
			return;
		}
		moves++;
		
		if (facing == Direction.RIGHT) {
			facing = Direction.DOWN;
		} else if (facing == Direction.UP) {
			facing = Direction.RIGHT;
		} else if (facing == Direction.LEFT) {
			facing = Direction.UP;
		} else {
			facing = Direction.LEFT;
		}
		notifyObservers(ActionType.Right);
	}

	/**
	 * Ant move one space in direction it is facing.
	 */
	public void move() {
		if(moves >= maxMoves) {
			return;
		}
		moves++;
		
		if (facing == Direction.LEFT) {
			currentX--;
			if (currentX < 0) {
				currentX += World.getWidth();
			}
		} else if (facing == Direction.RIGHT) {
			currentX = (currentX + 1) % World.getWidth();
		} else if (facing == Direction.UP) {
			currentY--;
			if (currentY < 0) {
				currentY += World.getHeight();
			}
		} else {
			currentY = (currentY + 1) % World.getHeight();
		}
		if (map[currentY][currentX] == 1) {
			foodEaten++;
			map[currentY][currentX] = 0;
		}
		notifyObservers(ActionType.Move);
	}

	/**
	 * @return <code>true</code> if food is in front of the ant,
	 *         <code>false</code> otherwise.
	 */
	public boolean isFoodAhead() {
		if (facing == Direction.LEFT) {
			int x = currentX - 1;
			if (x < 0) {
				x += World.getWidth();
			}
			if (map[x][currentY] == 1) {
				return true;
			}
		} else if (facing == Direction.RIGHT && map[(currentX + 1) % World.getWidth()][currentY] == 1) {
			return true;
		} else if (facing == Direction.UP) {
			int y = currentY - 1;
			if (y < 0) {
				y += World.getHeight();
			}
			if (map[currentX][y] == 1) {
				return true;
			}
		} else if (facing == Direction.DOWN && map[currentX][(currentY + 1) % World.getHeight()] == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Resets the map and puts the ant in its initial position.
	 */
	public void reset() {
		moves = 0;
		currentX = 0;
		currentY = 0;
		facing = Direction.RIGHT;
		map = World.getMapCopy();
		foodEaten = 0;
		fitness = 0;
	}

	/**
	 * Attaches observer to the ant.
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/**
	 * Notify all observers that action was performed.
	 */
	private void notifyObservers(ActionType type) {
		for (Observer o : observers) {
			o.update(type);
		}
	}

	@Override
	public String toString() {
		return root.toString();
	}

	/**
	 * @return tree depth
	 */
	public int getDepth() {
		return depthRecursion(root);
	}

	/**
	 * Helper method for <code>getDepth()</code>.
	 */
	private int depthRecursion(Action root) {
		if (root.children == null) {
			return root.depth;
		}
		int d = 0;
		for (Action c : root.children) {
			int cd = depthRecursion(c);
			if (cd > d) {
				d = cd;
			}
		}
		return d;
	}

	/**
	 * @return Total number of nodes in a tree.
	 */
	public int getNumberOfNodes() {
		return numberOfNodesRec(root);
	}

	/**
	 * Helper method for <code>getNumberOfNodes()</code>.
	 */
	private int numberOfNodesRec(Action a) {
		if (a.children == null) {
			return 1;
		}

		int nodes = 1;
		for (Action c : a.children) {
			nodes += numberOfNodesRec(c);
		}
		return nodes;
	}

	/**
	 * Returns copy of this ant.
	 */
	public Ant copy() {
		Action copyRoot = TreeGenerator.generateAction(root.type);
		copyRecursion(root, copyRoot);
		return new Ant(copyRoot, maxMoves);
	}

	/**
	 * Helper method for <code>copy()</code>.
	 */
	private void copyRecursion(Action originalRoot, Action copyRoot) {
		if (originalRoot.children == null) {
			return;
		}
		int n = originalRoot.children.length;
		copyRoot.children = new Action[n];
		for (int i = 0; i < n; i++) {
			copyRoot.children[i] = TreeGenerator.generateAction(originalRoot.children[i].type);
			copyRecursion(originalRoot.children[i], copyRoot.children[i]);
		}

	}
	
	public int getFoodEaten() {
		return foodEaten;
	}
	
	public void setParentFoodEaten(int parentFoodEaten) {
		this.parentFoodEaten = parentFoodEaten;
	}
	
	public int getParentFoodEaten() {
		return parentFoodEaten;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getMoves() {
		return moves;
	}
}
