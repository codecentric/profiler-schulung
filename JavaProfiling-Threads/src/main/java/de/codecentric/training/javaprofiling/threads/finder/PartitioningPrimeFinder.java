package de.codecentric.training.javaprofiling.threads.finder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;

/**
 * The idea of the PartitioningPrimeFinder is to create segments of numbers to check. Those segments can be worked on
 * individually by separate threads.
 * 
 * @author fabian.lange
 */
public class PartitioningPrimeFinder implements PrimeFinder {

	protected int numThreads;
	protected CountDownLatch latch = null;
	protected ExecutorService executor;
	protected List<Future<List<Integer>>> futures;

	public PartitioningPrimeFinder() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public PartitioningPrimeFinder(int numThreads) {
		if (numThreads == 1) {
			System.out.println("This example is pointless with a single thread.");
		}

		this.numThreads = numThreads;
		latch = new CountDownLatch(numThreads);
		executor = Executors.newFixedThreadPool(numThreads);
		futures = new ArrayList<Future<List<Integer>>>(numThreads);
	}

	@Override
	public List<Integer> findPrimes(int max) {
		System.out.printf("Finding Primes with %d threads%n", numThreads);

		int chunksize = max / numThreads;
		for (int segment = 1; segment <= numThreads; segment++) {

			int startpos = (segment - 1) * chunksize;
			int endpos = startpos + chunksize;

			if (segment == numThreads) {
				// the last segment has to reach to max
				endpos = max;
			}

			Callable<List<Integer>> task = new PrimeFinderCallable(startpos, endpos, segment);
			futures.add(executor.submit(task));
		}

		return waitForResults();
	}

	protected List<Integer> waitForResults() {
		System.out.printf("Waiting for %d PrimeFinderCallables to complete prime calculation%n", numThreads);
		List<Integer> results = new ArrayList<Integer>();
		try {
			executor.shutdown();
			latch.await();

			for (Future<List<Integer>> future : futures) {
				results.addAll(future.get());
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
		return results;
	}

	private class PrimeFinderCallable implements Callable<List<Integer>> {

		private final int startpos;
		private final int endpos;
		private final int segment;

		public PrimeFinderCallable(int startpos, int endpos, int segment) {
			this.startpos = startpos;
			this.endpos = endpos;
			this.segment = segment;
		}

		@Override
		public List<Integer> call() throws Exception {
			RangePrimeFinder primeFinder = new PrimeFinder5();
			long start = System.nanoTime();
			List<Integer> primes = primeFinder.findPrimes(startpos, endpos);
			long end = System.nanoTime();
			double time = (end - start) / 1E9;
			System.out.printf("Segment %d completed in %f seconds%n", segment, time);
			latch.countDown();
			return primes;
		}

	}

}
