package de.codecentric.training.javaprofiling.cpu;

import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer;
import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer1;
import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer2;
import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer3;
import de.codecentric.training.javaprofiling.cpu.consumer.PrimeConsumer4;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder1;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder2;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder3;
import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder4;

/**
 * Helper class.
 * 
 * @author patrick.peschlow
 */
public class RunnerHelper {

	/**
	 * Checks the max parameter. Exits if a misconfiguration is detected.
	 * 
	 * @param arg0
	 *            the max command line argument
	 * @return the max parameter value
	 */
	public static int evaluateMaxParam(String arg) {
		int max = -1;
		try {
			max = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			System.out.printf("Please provide a positive integer instead of %s%n", arg);
			System.exit(-1);
		}
		if (max < 2) {
			System.out.printf("Please provide a positive integer > 1 instead of %d%n", max);
			System.exit(-1);
		}
		return max;
	}

	/**
	 * Checks the finder strategy parameter. Exits if a misconfiguration is detected.
	 * 
	 * @param arg
	 *            the finder strategy command line argument
	 * @return the PrimeFinder to use
	 */
	public static PrimeFinder evaluateFinderParam(String arg) {
		int finderImpl = Integer.parseInt(arg);
		if (finderImpl < 1 || 4 < finderImpl) {
			System.out.printf("Unknown prime finder strategy: %d%n", finderImpl);
			System.exit(-1);
		}
		PrimeFinder primeFinder = null;
		switch (finderImpl) {
		case 1:
			primeFinder = new PrimeFinder1();
			break;
		case 2:
			primeFinder = new PrimeFinder2();
			break;
		case 3:
			primeFinder = new PrimeFinder3();
			break;
		case 4:
			primeFinder = new PrimeFinder4();
			break;
		default:
			break;
		}
		return primeFinder;
	}

	/**
	 * Checks the consumer strategy parameter. Exits if a misconfiguration is detected.
	 * 
	 * @param arg
	 *            the consumer strategy command line argument
	 * @return the PrimeConsumer to use
	 */
	public static PrimeConsumer evaluateConsumerParam(String arg) {
		int consumerImpl = Integer.parseInt(arg);
		if (consumerImpl < 1 || 4 < consumerImpl) {
			System.out.printf("Unknown prime consumer strategy: %d%n", consumerImpl);
			System.exit(-1);
		}
		PrimeConsumer primeConsumer = null;
		switch (consumerImpl) {
		case 1:
			primeConsumer = new PrimeConsumer1();
			break;
		case 2:
			primeConsumer = new PrimeConsumer2();
			break;
		case 3:
			primeConsumer = new PrimeConsumer3();
			break;
		case 4:
			primeConsumer = new PrimeConsumer4();
			break;
		default:
			break;
		}
		return primeConsumer;
	}

	/**
	 * Prints the time of a duration measured with System.nanoTime() in seconds.
	 * 
	 * @param start
	 *            the start time
	 * @param end
	 *            the end time
	 */
	public static void printFinishedTiming(long start, long end) {
		double time = (end - start) / 1E9;
		System.out.printf("Finished. Took %f seconds%n", time);
	}
}
