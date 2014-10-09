package de.codecentric.training.javaprofiling.memory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import de.codecentric.training.javaprofiling.cpu.finder.PrimeFinder4;

/**
 * @author fabian.lange
 */
@Service
public class BestPrimeFinderService implements PrimeFinderService {

	@Override
	public List<Integer> findPrimes(int max) {
		return new PrimeFinder4().findPrimes(max);
	}

}
