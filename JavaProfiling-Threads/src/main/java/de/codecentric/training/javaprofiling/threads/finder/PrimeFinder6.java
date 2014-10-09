package de.codecentric.training.javaprofiling.threads.finder;

import java.util.ArrayList;
import java.util.List;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder3;

/**
 * PrimeFinder working on primes in a range with step, uses PrimeFinder3#isPrime()
 * 
 * @author patrick.peschlow
 */
public class PrimeFinder6 extends PrimeFinder3 implements RangeStepPrimeFinder {

	/**
	 * {@inheritDoc}
	 * 
	 * Uses a library class optimized to do that job.
	 */
	@Override
	public List<Integer> findPrimes(int max) {
		return findPrimes(2, max, 1);
	}

	@Override
	public List<Integer> findPrimes(int min, int max) {
		return findPrimes(min, max, 1);
	}

	@Override
	public List<Integer> findPrimes(int min, int max, int step) {
		System.out.printf("Looking for primes between %d and %d with step %d%n", min, max, step);
		List<Integer> primes = new ArrayList<Integer>();
		for (int i = min; i < max; i += step) {
			if (isPrime(i)) {
				primes.add(i);
			}
		}
		return primes;
	}

}
