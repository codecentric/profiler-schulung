package de.codecentric.training.javaprofiling.threads.finder;

import java.util.List;
import java.util.concurrent.Callable;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;

/**
 * To even out the load on all Executors, this finder will give small and large numbers to each Callable. Somehow it
 * doesn't seem to work very well...
 * 
 * @author fabian.lange
 */
public class SteppingPrimeFinder extends PartitioningPrimeFinder implements PrimeFinder {

	final boolean bugfix;

	public SteppingPrimeFinder(boolean bugfix) {
		this.bugfix = bugfix;
	}

	public SteppingPrimeFinder(int numThreads, boolean bugfix) {
		super(numThreads);
		this.bugfix = bugfix;
	}

	@Override
	public List<Integer> findPrimes(int max) {
		System.out.printf("Finding Primes with %d threads%n", numThreads);

		for (int segment = 1; segment <= numThreads; segment++) {
			int startpos = segment - 1;
			int step = numThreads;
			if (bugfix) {
				// needed to avoid checking even numbers (which are never prime)
				startpos = 2 * segment - 1;
				step = numThreads * 2;
			}
			Callable<List<Integer>> task = new PrimeFinderCallable(startpos, max, step, segment);
			futures.add(executor.submit(task));
		}

		return waitForResults();
	}

	private class PrimeFinderCallable implements Callable<List<Integer>> {

		private final int startpos;
		private final int endpos;
		private final int step;
		private final int segment;

		public PrimeFinderCallable(int startpos, int endpos, int step, int segment) {
			this.startpos = startpos;
			this.endpos = endpos;
			this.step = step;
			this.segment = segment;
		}

		@Override
		public List<Integer> call() throws Exception {
			RangeStepPrimeFinder primeFinder = new PrimeFinder6();
			long start = System.nanoTime();
			List<Integer> primes = primeFinder.findPrimes(startpos, endpos, step);
			long end = System.nanoTime();
			double time = (end - start) / 1E9;
			System.out.printf("Segment %d completed in %f seconds%n", segment, time);
			latch.countDown();
			return primes;
		}

	}

}
