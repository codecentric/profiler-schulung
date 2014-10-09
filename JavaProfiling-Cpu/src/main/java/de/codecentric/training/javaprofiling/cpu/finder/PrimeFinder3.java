package de.codecentric.training.javaprofiling.cpu.finder;

import java.util.ArrayList;
import java.util.List;

/**
 * Naive prime finder using an inefficient method, but stopping the iteration quickly.
 * 
 * @author patrick.peschlow
 */
public class PrimeFinder3 implements PrimeFinder {
	/**
	 * {@inheritDoc}
	 * 
	 * Iterates over all integers and independently checks for each integer whether it is prime.
	 */
	@Override
	public List<Integer> findPrimes(int max) {
		List<Integer> primes = new ArrayList<Integer>();
		for (int i = 0; i < max; i++) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}

	/**
	 * Checks whether the provided number is prime.
	 * 
	 * @param number
	 *            the number to check
	 * @return whether number is prime
	 */
	protected boolean isPrime(int number) {
		if (number < 2) {
			return false;
		} else {
			// iterate over all integers up to sqrt(number)
			for (int i = 2, limit = (int) Math.sqrt(number); i <= limit; i++) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}
	}

}
