package de.codecentric.training.javaprofiling.cpu.consumer;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.PrimeFinderRunner;

/**
 * Badly written prime consumer using String concatenation in a loop, a non-primitive Long sum calculation, and a badly
 * placed code guard.
 * 
 * @author patrick.peschlow
 */
public class PrimeConsumer1 implements PrimeConsumer {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String consumePrimes(List<Integer> primes) {
		Long sum = 0L;
		String allPrimes = "";
		for (Integer prime : primes) {
			sum += prime;
			allPrimes += prime + " ";
		}
		String output = "The sum of all primes is " + sum + ".";
		if (PrimeFinderRunner.PRINT_PRIMES) {
			output += "\nList of primes: " + allPrimes;
		}
		return output;
	}
}
