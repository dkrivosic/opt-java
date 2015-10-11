package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

/**
 * Immutable vector of bits each representing value of a variable.
 */
public class BitVector {
	protected boolean[] bits;

	public BitVector(Random rand, int numberOfBits) {
		bits = new boolean[numberOfBits];
		for (int i = 0; i < numberOfBits; i++) {
			bits[i] = rand.nextBoolean();
		}
	}

	public BitVector(boolean... bits) {
		this.bits = bits;
	}

	public BitVector(int n) {
		bits = new boolean[n];
	}

	/**
	 * @return value of index-th variable.
	 */
	public boolean get(int index) {
		return bits[index];
	}

	/**
	 * @return number of variables.
	 */
	public int getSize() {
		return bits.length;
	}

	@Override
	public String toString() {
		String str = "";
		for (boolean x : bits) {
			if (x) {
				str += "1";
			} else {
				str += "0";
			}
		}
		return str;
	}

	/**
	 * Creates mutable copy of current <code>BitVector</code>.
	 * 
	 * @return mutable copy
	 */
	public MutableBitVector copy() {
		boolean copy[] = Arrays.copyOf(bits, getSize());
		return new MutableBitVector(copy);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BitVector)) return false;
		BitVector that = (BitVector) obj;
		if(this.getSize() != that.getSize()) return false;
		
		for(int i = 0; i < this.getSize(); i++) {
			if(this.get(i) != that.get(i)) return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		long h = 1234;
	      for (int i = bits.length; --i >= 0; ) {
	    	  int num = bits[i] ? 1 : 0;
	           h ^= num * (i + 1);
	      }
	      return (int)((h >> 32) ^ h);
	}
}
