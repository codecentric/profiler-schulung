package de.codecentric.training.javaprofiling.cpu;

import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.evaluateConsumerParam;
import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.evaluateFinderParam;
import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.evaluateMaxParam;
import static de.codecentric.training.javaprofiling.cpu.RunnerHelper.printFinishedTiming;

import java.util.List;

import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;

/**
 * A "Prime Finder" example illustrating performance bottlenecks. The application computes the set of prime numbers up
 * to the specified maximum integer value (which is called the "finder" part of the application). Then it computes the
 * sum of these prime numbers and optionally outputs them to STDOUT (the "consumer" part of the application).
 * 
 * A good starting point for sensible run times of the prime finder is around 500000, depending on the CPU speed.
 * 
 * @author patrick.peschlow
 */
public class PrimeFinderRunner {
	/**
	 * Whether to print all identified primes to STDOUT.
	 */
	public static boolean PRINT_PRIMES = false;

	/**
	 * Main method.
	 * 
	 * @param args
	 *            command-line parameters
	 */
	public static void main(String[] args) {
		// special shortcut
		if (args.length == 1) {
			String arg = args[0];
			if (arg.equals("default")) {
				arg = "500000";
			}
			args = new String[3];
			args[0] = arg;
			args[1] = "1";
			args[2] = "1";
		}
		// parameter count check
		if (args.length != 3) {
			System.out
					.println("Configuration error: Please provide three parameters in the following order: <max> <finder implementation> <consumer implementation>");
			System.out.println("Details:");
			System.out
					.println("<max> describes the range of integers to consider in the prime finder test (0 .. max-1) - depends on PC speed try 500.000");
			System.out.println("<finder implementation> choose the implementation of the prime finder part (1 .. 4)");
			System.out
					.println("<consumer implementation> choose the implementation of the prime consumer part (1 .. 4)");
			System.exit(-1);
		}

		// evaluate command line arguments
		final int max = evaluateMaxParam(args[0]);
		final PrimeFinder primeFinder = evaluateFinderParam(args[1]);
		final PrimeConsumer primeConsumer = evaluateConsumerParam(args[2]);

		// identify and gather prime numbers
		System.out.println("Finding primes.");
		long start = System.nanoTime();
		final List<Integer> primes = primeFinder.findPrimes(max);
		long end = System.nanoTime();
		printFinishedTiming(start, end);
		System.out.printf("Found %d primes.%n", primes.size());

		// consume the list of primes
		System.out.println("Consuming primes.");
		start = System.nanoTime();
		final String output = primeConsumer.consumePrimes(primes);
		end = System.nanoTime();
		printFinishedTiming(start, end);
		System.out.println(output);
	}

}
