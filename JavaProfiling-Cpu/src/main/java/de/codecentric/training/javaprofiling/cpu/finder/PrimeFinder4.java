package de.codecentric.training.javaprofiling.cpu.finder;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.lib.MathUtils;

/**
 * Efficient prime finder based on a clever library routine.
 * 
 * @author patrick.peschlow
 */
public class PrimeFinder4 implements PrimeFinder {
	/**
	 * {@inheritDoc}
	 * 
	 * Uses a library classed optimized to do that job.
	 */
	@Override
	public List<Integer> findPrimes(int max) {
		return MathUtils.findPrimesQuickly(max);
	}
}
