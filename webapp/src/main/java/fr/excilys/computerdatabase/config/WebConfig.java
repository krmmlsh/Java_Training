package fr.excilys.computerdatabase.config;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@EnableWebSecurity
@Configuration
@ComponentScan("fr.excilys.computerdatabase.*")
@PropertySource(value = { "classpath:application.properties" })
//@Import({ SecurityConfig.class })
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;
    
	private HikariDataSource ds;

	
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
	
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}







