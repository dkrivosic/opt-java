package hr.fer.zemris.trisat;

/**
 * Mutable vector of bits each representing value of a variable.
 */
public class MutableBitVector extends BitVector {
	
	public MutableBitVector(int n) {
		super(n);
	}
	
	public MutableBitVector(boolean ... bits) {
		super(bits);
	}
	
	/**
	 * Set index-th variable to <code>value</code>.
	 */
	public void set(int index, boolean value) {
		super.bits[index] = value;
	}
	
}
