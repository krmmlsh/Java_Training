package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnectionTest {

	private final Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	private static HikariConfig config = new HikariConfig("src/test/resources/hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);
 
	
	private DatabaseConnectionTest() {}
	
	public static Connection getConnection() {
		try{
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + " Error sql connection");
		}
	}
	

}
