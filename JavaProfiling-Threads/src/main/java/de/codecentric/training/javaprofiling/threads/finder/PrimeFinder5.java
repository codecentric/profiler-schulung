package de.codecentric.training.javaprofiling.threads.finder;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder3;

/**
 * PrimeFinder working on primes in a range, uses PrimeFinder3#isPrime()
 * 
 * @author patrick.peschlow
 */
public class PrimeFinder5 extends PrimeFinder3 implements RangePrimeFinder {

	/**
	 * {@inheritDoc}
	 * 
	 * Uses a library classed optimized to do that job.
	 */
	@Override
	public List<Integer> findPrimes(int max) {
		return findPrimes(2, max);
	}

	@Override
	public List<Integer> findPrimes(int min, int max) {
		System.out.printf("Looking for primes between %d and %d%n", min, max);
		List<Integer> primes = new ArrayList<Integer>();
		for (int i = min; i < max; i++) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}

}
