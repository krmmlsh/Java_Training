package fr.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.model.Company;

@Component
public class CompanyMapper {

	
	
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
