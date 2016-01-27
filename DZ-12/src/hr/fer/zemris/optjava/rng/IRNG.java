package hr.fer.zemris.optjava.rng;

/**
 * Random number generator interface. 
 */
public interface IRNG {

	/**
	 * Returns uniformly distributed double value from the interval [0,1>
	 * 
	 * @return random generated double value
	 */
	public double nextDouble();
	
	/**
	 * Returns uniformly distributed double value from the interval [min,max>
	 * 
	 * @param min lower interval limit
	 * @param max upper interval limit
	 * @return random generated double value
	 */
	public double nextDouble(double min, double max);
	
	/**
	 * Returns uniformly distributed float value from the interval [0,1>
	 * 
	 * @return random generated float value
	 */
	public float nextFloat();
	
	/**
	 * Returns uniformly distributed float value from the interval [min,max>
	 * 
	 * @param min lower interval limit
	 * @param max upper interval limit
	 * @return random generated float value
	 */
	public float nextFloat(double min, double max);
	
	/**
	 * Returns uniformly distributed integer value from the interval [0,1>
	 * 
	 * @return random generated integer value
	 */
	public int nextInt();
	
	/**
	 * Returns uniformly distributed integer value from the interval [min,max>
	 * 
	 * @param min lower interval limit
	 * @param max upper interval limit
	 * @return random generated integer value
	 */
	public int nextInt(int min, int max);
	
	/**
	 * Returns uniformly distributed boolean value from the interval [0,1>
	 * 
	 * @return random generated boolean
	 */
	public boolean nextBoolean();

	/**
	 * Returns normally distributed double value with parameters (0,1)
	 * 
	 * @return random generated double value
	 */
	public double nextGaussian();
	
}

