package de.codecentric.training.javaprofiling.memory.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author fabian.lange
 */
@Service
@Qualifier("Storage1")
public class PrimeStorageService1 implements PrimeStorageService {

	private final HashMap<Integer, List<Integer>> primeCache = new HashMap<Integer, List<Integer>>();

	@Override
	public void addPrimes(int max, List<Integer> primes) {
		primeCache.put(max, primes);
	}

	@Override
	public Map<Integer, Integer> getPrimeCountsPerMax() {
		HashMap<Integer, Integer> primeCounts = new HashMap<Integer, Integer>(primeCache.size());
		for (Integer maximum : primeCache.keySet()) {
			primeCounts.put(maximum, primeCache.get(maximum).size());
		}
		return primeCounts;
	}

	@Override
	public void clear() {
		primeCache.clear();
	}

}
