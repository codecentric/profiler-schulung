package de.codecentric.training.javaprofiling.memory.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@Qualifier("StorageDB")
public class PrimeStorageServiceDatabase implements PrimeStorageService {

	private List<Integer> knownPrimes = Collections.emptyList();

	private Connection dbConnection;

	public PrimeStorageServiceDatabase() {
		super();
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			dbConnection = DriverManager.getConnection("jdbc:hsqldb:mem:primedb", "SA", "");

			Statement statement = dbConnection.createStatement();
			statement.execute("create table primeinfo (maximum int, primecount int)");
		} catch (SQLException e) {
			throw new RuntimeException("Could not create in memory database.");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("HSQLDB driver not found.");
		}
	}

	@Override
	public void addPrimes(int max, List<Integer> primes) {
		try {
			boolean knownMax = false;
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery("select primecount from primeinfo where maximum = " + max);
			if (resultSet.next()) {
				knownMax = true;
			}

			if (!knownMax) {
				Statement insertStatement = dbConnection.createStatement();
				insertStatement.executeUpdate("insert into primeinfo (maximum, primecount) values (" + max + ", "
						+ primes.size() + ")");
				if (primes.size() > knownPrimes.size()) {
					knownPrimes = primes;
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Could not add primes to DB.");
		}

	}

	@Override
	public Map<Integer, Integer> getPrimeCountsPerMax() {
		Map<Integer, Integer> results = new HashMap<Integer, Integer>();
		try {
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery("select maximum, primecount from primeinfo");
			while (resultSet.next()) {
				results.put(resultSet.getInt(1), resultSet.getInt(2));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Could not insert into DB.");
		}
		return results;
	}

	@Override
	public void clear() {
		knownPrimes = Collections.emptyList();
		try {
			Statement statement = dbConnection.createStatement();
			statement.executeUpdate("delete from primeinfo");
		} catch (SQLException e) {
			throw new RuntimeException("Could not flush DB.");
		}

	}

}
