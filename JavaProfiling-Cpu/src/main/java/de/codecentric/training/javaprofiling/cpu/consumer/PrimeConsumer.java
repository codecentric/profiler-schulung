package de.codecentric.training.javaprofiling.cpu.consumer;

import java.util.List;

/**
 * Interface for consumers of prime numbers, e.g., for printing or performing calculations with them.
 * 
 * @author patrick.peschlow
 */
public interface PrimeConsumer {
	/**
	 * Consumes a list of prime numbers.
	 * 
	 * @param primes
	 *            the list of primes
	 * @return textual output stating the results obtained by consuming the primes
	 */
	public String consumePrimes(List<Integer> primes);
}
