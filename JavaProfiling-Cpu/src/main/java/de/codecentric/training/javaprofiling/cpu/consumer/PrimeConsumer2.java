package de.codecentric.training.javaprofiling.cpu.consumer;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.PrimeFinderRunner;

/**
 * Badly written prime consumer using a non-primitive Long sum calculation, and a badly placed code guard.
 * 
 * @author patrick.peschlow
 */
public class PrimeConsumer2 implements PrimeConsumer {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String consumePrimes(List<Integer> primes) {
		Long sum = 0L;
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
