package de.codecentric.training.javaprofiling.threads;

import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.evaluateMaxParam;
import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.printFinishedTiming;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder3;
import de.codecentric.training.javaprofiling.threads.finder.PartitioningPrimeFinder;
import de.codecentric.training.javaprofiling.threads.finder.SteppingPrimeFinder;

/**
 * @author fabian.lange
 */
public class ForkJoinRunner {

	/**
	 * Number of threads used for the fork join benchmarks.
	 */
	private static final int NUM_THREADS = 4;

	public static void main(String[] args) {
		// parameter count check
		if (args.length != 2) {
			System.out
					.println("Configuration error: Please provide two parameters in the following order: <max> <executor implementation>");
			System.out.println("Details:");
			System.out
					.println("<max> describes the range of integers to consider in the prime finder test (0 .. max-1) - depends on PC speed try 500.000");
			System.out.println("<finder implementation> choose the implementation of the prime finder part (1 .. 2)");
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

		final PrimeFinder primeFinder = ForkJoinRunner.evaluateForkJoinParam(args[1]);
		System.out.println("Finding primes.");
		start = System.nanoTime();
		primes = primeFinder.findPrimes(max);
		end = System.nanoTime();
		printFinishedTiming(start, end);
		System.out.printf("Found %d primes.%n", primes.size());
	}

	/**
	 * Checks the finder strategy parameter. Exits if a misconfiguration is detected.
	 * 
	 * @param arg
	 *            the finder strategy command line argument
	 * @return the PrimeFinder to use
	 */
	public static PrimeFinder evaluateForkJoinParam(String arg) {
		int finderImpl = Integer.parseInt(arg);
		if (finderImpl < 1 || 3 < finderImpl) {
			System.out.printf("Unknown prime finder strategy: %d%n", finderImpl);
			System.exit(-1);
		}
		PrimeFinder primeFinder = null;
		switch (finderImpl) {
		case 1:
			primeFinder = new PartitioningPrimeFinder(NUM_THREADS);
			break;
		case 2:
			primeFinder = new SteppingPrimeFinder(NUM_THREADS, false);
			break;
		case 3:
			primeFinder = new SteppingPrimeFinder(NUM_THREADS, true);
			break;
		default:
			break;
		}
		return primeFinder;
	}

}