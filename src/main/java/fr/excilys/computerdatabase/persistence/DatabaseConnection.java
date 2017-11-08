package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {

	
	private HikariDataSource ds;
 
	public static DatabaseConnection databaseConnection;
	
	private DatabaseConnection(String file) {
		HikariConfig config = new HikariConfig(file);
		ds = new HikariDataSource(config);
	}
	
	public static DatabaseConnection getInstance (String file) {
		if (databaseConnection == null) {
			databaseConnection = new DatabaseConnection(file);
		}
		return databaseConnection;
	}
	
	public Connection getConnection() {
		try{
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + " Error sql connection");
		}
	}
	

}
