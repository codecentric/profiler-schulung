package de.codecentric.training.javaprofiling.cpu.finder;

import java.util.List;

/**
 * Interface for prime finder implementations.
 * 
 * @author patrick.peschlow
 */
public interface PrimeFinder {
	/**
	 * Finds all prime numbers smaller than max.
	 * 
	 * @param max
	 * @return a list of primes
	 */
	public List<Integer> findPrimes(int max);
}
