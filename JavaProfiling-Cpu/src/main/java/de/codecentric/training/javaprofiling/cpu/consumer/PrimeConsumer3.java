package de.codecentric.training.javaprofiling.cpu.consumer;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.PrimeFinderRunner;

/**
 * Prime consumer that avoids some inefficiencies but may still create an unneeded long String due to badly placed code
 * guard.
 * 
 * @author patrick.peschlow
 */
public class PrimeConsumer3 implements PrimeConsumer {

	/**
	 * {@inheritDoc}
	 * 
	 * Uses primitive data types for arithmetic and a StringBuilder for text concatenation.
	 */
	@Override
	public String consumePrimes(List<Integer> primes) {
		long sum = 0L;
		StringBuilder sb = new StringBuilder();
		for (Integer prime : primes) {
			sum += prime;
			sb.append(prime).append(" ");
		}
		String output = "The sum of all primes is " + sum + ".";
		if (PrimeFinderRunner.PRINT_PRIMES) {
			output += "\nList of primes: " + sb.toString();
		}
		return output;
	}
}
