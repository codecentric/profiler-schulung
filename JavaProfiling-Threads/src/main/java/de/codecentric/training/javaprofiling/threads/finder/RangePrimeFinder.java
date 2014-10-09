package de.codecentric.training.javaprofiling.threads.finder;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;

/**
 * @author fabian.lange
 * @author patrick.peschlow
 */
public interface RangePrimeFinder extends PrimeFinder {

	/**
	 * Finds all prime numbers smaller than max but larger than min.
	 * 
	 * @param min
	 * @param max
	 * @return a list of primes
	 */
	public List<Integer> findPrimes(int min, int max);
}
