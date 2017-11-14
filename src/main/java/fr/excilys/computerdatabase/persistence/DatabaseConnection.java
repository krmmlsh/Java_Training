package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("/application.properties")
public class DatabaseConnection {
	@Value("${jdbc.driverClassName}")
	private String driverClassName;
	@Value("${jdbc.url}")
	private String jdbcURL;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;

	private HikariDataSource ds;
	
	//@Autowired
	//private Environment env;

	@Bean
	public HikariDataSource dataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcURL);
		config.setDriverClassName(driverClassName);
		config.setUsername(username);
		config.setPassword(password);
		ds = new HikariDataSource(config);
		return ds;
		//ds = new DriverManagerDataSource();
		//ds.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		//ds.setUrl(env.getRequiredProperty("jdbc.url"));
		//ds.setUsername(env.getRequiredProperty("jdbc.username"));
		//ds.setPassword(env.getRequiredProperty("jdbc.password"));
		//return ds;
	}


	public Connection getConnection() throws SQLException {
			return ds.getConnection();
	}

}
