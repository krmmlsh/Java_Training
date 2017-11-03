package fr.excilys.computerdatabase.service;

import java.util.Map;

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
	public Map<Integer, String> getAllCompanies(){
		return companyDao.getCompanies();
	}
	
	/**
	 * Get a single company.
	 * @return A company object.
	 */
	public int getCompany(String name) {
		return companyDao.getCompany(name);
	}
	
	
	public String getCompany(int id) {
		return companyDao.getCompany(id);
	}
	
}
