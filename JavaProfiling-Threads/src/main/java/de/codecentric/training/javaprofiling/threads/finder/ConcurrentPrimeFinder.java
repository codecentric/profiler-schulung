package de.codecentric.training.javaprofiling.threads.finder;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;

/**
 * Threaded implementation looking for primes. This class starts threads, so it should not be shared itself.
 * 
 * @author fabian.lange
 * @author patrick.peschlow
 */
public class ConcurrentPrimeFinder implements PrimeFinder {

	private final List<Integer> primes;
	private final ExecutorService executor;

	public ConcurrentPrimeFinder(ExecutorService executor) {
		this.executor = executor;
		// use Vector here because it is thread-safe (synchronized)
		this.primes = new Vector<Integer>();
	}

	/**
	 * Iterates over all integers and independently checks for each integer whether it is prime.
	 * 
	 * @throws InterruptedException
	 */
	@Override
	public List<Integer> findPrimes(int max) {
		for (int i = 0; i < max; i++) {
			executor.execute(new PrimeChecker(i));
		}
		waitForCompletion();
		return primes;
	}

	private void waitForCompletion() {
		executor.shutdown();

		boolean complete = false;
		try {
			complete = executor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		if (!complete) {
			throw new RuntimeException("Timeout!");
		}
	}

	private class PrimeChecker implements Runnable {

		private final int number;

		public PrimeChecker(int number) {
			this.number = number;
		}

		/**
		 * Checks whether the provided number is prime. Same algorithm used by PrimeFinder3.
		 * 
		 * @param number
		 *            the number to check
		 * @return whether number is prime
		 */
		private void addIfNumberIsPrime() {
			if (number < 2) {
				return;
			} else {
				// iterate over all integers up to sqrt(number)
				for (int i = 2, limit = (int) Math.sqrt(number); i <= limit; i++) {
					if (number % i == 0) {
						return;
					}
				}
				primes.add(number);
			}
		}

		@Override
		public void run() {
			addIfNumberIsPrime();
		}

	}
}
