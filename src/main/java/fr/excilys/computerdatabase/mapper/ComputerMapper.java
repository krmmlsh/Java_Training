package fr.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.CompanyDao;

public class ComputerMapper {
	
	
	private static ComputerMapper computerMapper;
	
	private ComputerMapper() {
		
	}
	
	
	public synchronized static ComputerMapper getInstance() {
		if (computerMapper == null)
			computerMapper = new ComputerMapper();
		return computerMapper;
	}

	/**
	 * Fill computer with ResultSet values.
	 * 
	 * @param rs
	 *            ResultSet from the select query.
	 * @return Computer newly filled.
	 * @throws SQLException
	 */
	public Computer createComputerFromDatabase(ResultSet rs, CompanyDao companyDao) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setCompany(companyDao.getCompany(rs.getInt("company_id")));
		computer.setIntroducedDate((rs.getDate("introduced") != null) ? rs.getDate("introduced").toLocalDate().plusDays(1) : null);
		computer.setDiscontinuedDate((rs.getDate("discontinued") != null) ? rs.getDate("discontinued").toLocalDate().plusDays(1) : null);
		return computer;

	}

}
