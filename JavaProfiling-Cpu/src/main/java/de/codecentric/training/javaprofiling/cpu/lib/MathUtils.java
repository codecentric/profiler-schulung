package de.codecentric.training.javaprofiling.cpu.lib;

import java.util.ArrayList;
import java.util.List;

/**
 * Math utility class containing a quite efficient prime finder method (Sieve of Eratosthenes). The implementation can
 * still be tuned, but we have to measure first in order to decide whether performance tuning is really needed.
 * 
 * @author patrick.peschlow
 */
public class MathUtils {
	/**
	 * Used to avoid errors because of an integer overflow.
	 */
	private static final int INTOVERFLOWBOUNDARY = (int) Math.sqrt(Integer.MAX_VALUE);

	/**
	 * Finds all primes among the positive integers smaller than {@code max}. Uses the sieve method.
	 * 
	 * @param max
	 *            upper bound for the integers to consider
	 * @return a list of all primes smaller than max
	 */
	public static List<Integer> findPrimesQuickly(int max) {
		List<Integer> primes = new ArrayList<Integer>();
		// helper array to mark numbers identified to be no primes
		boolean[] marked = new boolean[max];
		for (int i = 2; i < max; i++) {
			if (!marked[i]) {
				primes.add(i);
				// check to not consider values for j that are beyond Integer.MAX_VALUE
				if (i <= INTOVERFLOWBOUNDARY) {
					for (int j = i * i; j > 0 && j < max; j += i) {
						marked[j] = true;
					}
				}
			}
		}
		return primes;
	}
}
