package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.Driver;

public class DatabaseConnection {

	private final Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	
	public static String url = "jdbc:mysql://localhost:3306/computer-database-db?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
	public static final String username = "admincdb";
	public static final String password = "qwerty1234";

	public static Connection getConnection() {
		try {
			
			DriverManager.registerDriver(new Driver());
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage() + " Error sql connection");
		}
	}
	

}
