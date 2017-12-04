package fr.excilys.computerdatabase.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MyWebAppInitialyzer implements WebApplicationInitializer{
	@Override
	  public void onStartup(final ServletContext container) throws ServletException {
	    final AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
	    ctx.register(WebConfig.class);
	    ctx.setServletContext(container);
	    final ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));
	    servlet.setLoadOnStartup(1);
	    servlet.addMapping("/computer",
	    		"/webservice/addComputer",
	    		"/webservice/dashboard",
	    		"/webservice/editComputer",
	    		"/webservice/deleteComputer",
	    		"/webservice/deleteCompany",
	    		"/webservice/search",
	    		"/webservice/companies",
	    		"/webservice/company",
	    		"/webservice/computer",
	    		"/login");
	  }
}
