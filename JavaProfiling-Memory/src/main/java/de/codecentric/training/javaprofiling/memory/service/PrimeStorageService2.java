package de.codecentric.training.javaprofiling.memory.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author fabian.lange
 */
@Service
@Qualifier("Storage2")
public class PrimeStorageService2 implements PrimeStorageService {

	private List<Integer> knownPrimes = Collections.emptyList();

	private final Map<Integer, Integer> primeCounts = new HashMap<Integer, Integer>();

	@Override
	public void addPrimes(int max, List<Integer> primes) {
		if (!primeCounts.containsKey(max)) {
			primeCounts.put(max, primes.size());
			if (primes.size() > knownPrimes.size()) {
				knownPrimes = primes;
			}
		}
	}

	@Override
	public Map<Integer, Integer> getPrimeCountsPerMax() {
		return primeCounts;
	}

	@Override
	public void clear() {
		knownPrimes = Collections.emptyList();
		primeCounts.clear();
	}

}
