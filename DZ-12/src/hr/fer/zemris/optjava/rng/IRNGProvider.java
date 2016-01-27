package hr.fer.zemris.optjava.rng;

/**
 * Interface that represents objects that contain random number generator and
 * offer that generator to users with method {@link #getRNG()}. Objects that
 * implement this interface must not create new instance of random number
 * generator each time {@link #getRNG()} method is called. They must have their
 * own random number generator or access to a collection of existing generators
 * from which they fetch and return generators or they create generator on
 * request and then cache random number generator for each method caller.
 */
public interface IRNGProvider {

	/**
	 * Method for fetching the random number generator that belongs to this
	 * object.
	 * 
	 * @return random number generator
	 */
	public IRNG getRNG();
}
