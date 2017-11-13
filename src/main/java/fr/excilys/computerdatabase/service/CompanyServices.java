package fr.excilys.computerdatabase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.context.Context;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.persistence.CompanyDao;

@Component
public class CompanyServices {
	
	@Autowired
	private CompanyDao companyDao;

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	/**
	 * List all the companies from the database.
	 * @return a list of Company objects.
	 */
	public List<Company> getAllCompanies(){
		return companyDao.getCompanies();
	}
	
	/**
	 * Get a single company.
	 * @return A company object.
	 */
	public Company getCompanyByName(String name) {
		return companyDao.getCompanyByName(name);
	}
	
	
	public Company getCompany(int id) {
		return companyDao.getCompany(id);
	}

	public void deleteCompany(int id) {
		companyDao.deleteCompany(id);
	}
	
}
