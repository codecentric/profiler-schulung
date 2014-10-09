package de.codecentric.training.javaprofiling.threads.finder;

import java.util.List;

/**
 * @author fabian.lange
 * @author patrick.peschlow
 */
public interface RangeStepPrimeFinder extends RangePrimeFinder {

	/**
	 * Finds all prime numbers smaller than max but larger than min, thereby taking steps.
	 * 
	 * @param min
	 * @param max
	 * @param step
	 * @return a list of primes
	 */
	public List<Integer> findPrimes(int min, int max, int step);
}
