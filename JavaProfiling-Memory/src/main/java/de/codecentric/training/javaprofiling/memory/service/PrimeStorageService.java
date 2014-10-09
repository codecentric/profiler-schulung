package de.codecentric.training.javaprofiling.memory.service;

import java.util.List;
import java.util.Map;

/**
 * Storage Service for remembering calculated primes
 * 
 * @author fabian.lange
 */
public interface PrimeStorageService {

	public void addPrimes(int max, List<Integer> primes);

	public Map<Integer, Integer> getPrimeCountsPerMax();

	public void clear();
}