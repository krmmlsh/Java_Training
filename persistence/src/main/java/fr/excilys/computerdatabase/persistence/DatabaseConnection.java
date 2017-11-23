package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DatabaseConnection {

    @Autowired
    private Environment environment;
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(restDataSource());
		sessionBuilder.scanPackages("fr.excilys.computerdatabase.model");
		return sessionBuilder.buildSessionFactory();
	}

	@Bean
	public DataSource restDataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName( environment.getRequiredProperty("jdbc.driverClassName"));
		config.setJdbcUrl(environment.getRequiredProperty("jdbc.url"));
		config.setUsername(environment.getRequiredProperty("jdbc.username"));
		config.setPassword(environment.getRequiredProperty("jdbc.password"));
		ds = new HikariDataSource(config);
		return ds;
	}


	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private HikariDataSource ds;

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	

}
