package de.codecentric.training.javaprofiling.threads;

import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.evaluateMaxParam;
import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.printFinishedTiming;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder3;
import de.codecentric.training.javaprofiling.threads.finder.ConcurrentPrimeFinder;

/**
 * @author fabian.lange
 */
public class ConcurrentRunner {

	public static void main(String[] args) {
		// parameter count check
		if (args.length != 2) {
			System.out
					.println("Configuration error: Please provide two parameters in the following order: <max> <executor implementation>");
			System.out.println("Details:");
			System.out
					.println("<max> describes the range of integers to consider in the prime finder test (0 .. max-1) - depends on PC speed try 500.000");
			System.out
					.println("<executor implementation> choose the implementation of the executor for prime finder part (1 .. 5)");
			System.exit(-1);
		}

		// evaluate command line arguments
		final int max = evaluateMaxParam(args[0]);
		final PrimeFinder referencePrimeFinder = new PrimeFinder3();

		System.out.println("Finding primes with reference Implementation 3.");
		long start = System.nanoTime();
		List<Integer> primes = referencePrimeFinder.findPrimes(max);
		long end = System.nanoTime();
		printFinishedTiming(start, end);
		System.out.printf("Found %d primes.%n", primes.size());

		ExecutorService executor = ConcurrentRunner.evaluateExecutorParam(args[1]);
		final PrimeFinder primeFinder = new ConcurrentPrimeFinder(executor);

		System.out.println("Finding primes.");
		start = System.nanoTime();
		primes = primeFinder.findPrimes(max);
		end = System.nanoTime();
		printFinishedTiming(start, end);
		System.out.printf("Found %d primes.%n", primes.size());
	}

	/**
	 * Checks the executor strategy parameter. Exits if a misconfiguration is detected.
	 * 
	 * @param arg
	 *            the executor strategy command line argument
	 * @return the PrimeFinder to use
	 */
	public static ExecutorService evaluateExecutorParam(String arg) {
		int finderImpl = Integer.parseInt(arg);
		if (finderImpl < 1 || 5 < finderImpl) {
			System.out.printf("Unknown executor : %d%n", finderImpl);
			System.exit(-1);
		}
		ExecutorService executor = null;
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		switch (finderImpl) {
		case 1:
			executor = Executors.newSingleThreadExecutor();
			System.out.printf("Using Single Thread Executor%n");
			break;
		case 2:
			executor = Executors.newCachedThreadPool();
			System.out.printf("Using Cached Thread Executor%n");
			break;
		case 3:
			int processors = availableProcessors > 1 ? availableProcessors / 2 : availableProcessors;
			executor = Executors.newFixedThreadPool(processors);
			System.out.printf("Using %d Threads%n", processors);
			break;
		case 4:
			executor = Executors.newFixedThreadPool(availableProcessors);
			System.out.printf("Using %d Threads%n", availableProcessors);
			break;
		case 5:
			executor = Executors.newFixedThreadPool(availableProcessors * 2);
			System.out.printf("Using %d Threads%n", availableProcessors * 2);
			break;
		default:
			break;
		}
		return executor;
	}

}