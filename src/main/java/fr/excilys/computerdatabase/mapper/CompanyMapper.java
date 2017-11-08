package fr.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.Computer;

public class CompanyMapper {

	private static CompanyMapper computerMapper;
	
	
	private CompanyMapper() {
		
	}
	
	
	public synchronized static CompanyMapper getInstance() {
		if (computerMapper == null)
			computerMapper = new CompanyMapper();
		return computerMapper;
	}
	

	/**
	 * Fill company with ResultSet values.
	 * 
	 * @param rs ResultSet from the select query.
	 * @return Computer newly filled.
	 * @throws SQLException
	 */
	public Company createCompanyFromDatabase(ResultSet rs) throws SQLException  {
		Company company = new Company();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));
		return company;
	}
	
}
