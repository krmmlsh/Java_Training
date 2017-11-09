package fr.excilys.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.persistence.CompanyDao;

public class CompanyServices {

	private static CompanyServices cs = new CompanyServices();
	
	private CompanyDao companyDao = CompanyDao.getInstance();

	
	private CompanyServices () {
		
	}
	
	public static CompanyServices getCompanyServices() {
		return cs;
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
