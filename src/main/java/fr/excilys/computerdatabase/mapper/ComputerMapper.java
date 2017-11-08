package fr.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.CompanyDao;
import fr.excilys.computerdatabase.service.CompanyServices;
import fr.excilys.computerdatabase.servlet.ComputerDTO;

public class ComputerMapper {
	
	
	private static ComputerMapper computerMapper;
	
	private CompanyServices companyServices = CompanyServices.getCompanyServices();
	
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
	public Computer createComputerFromDatabase(ResultSet rs, CompanyDao companyDao) throws SQLException  {
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setCompany(rs.getString("comName"));
		computer.setIntroducedDate(Util.convertStringToLocalDate(rs.getString("introduced"), "yyyy-MM-dd"));
		computer.setDiscontinuedDate(Util.convertStringToLocalDate(rs.getString("discontinued"), "yyyy-MM-dd"));
		return computer;
	}
	
	public Computer buildComputerFromRequest(HttpServletRequest request) {
		return new Computer.Builder().name(request.getParameter("computerName"))
				.compId(Integer.valueOf(request.getParameter("companyId")))
				.introducedDate(Util.convertStringToLocalDate(request.getParameter("introduced"), "dd/MM/yyyy"))
				.discontinuedDate(Util.convertStringToLocalDate(request.getParameter("discontinued"), "dd/MM/yyyy"))
				.build();	
	}
	
	public ComputerDTO computerToComputerDTO(Computer computer) {
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
			.introducedDate((computer.getIntroducedDate()!= null) ? computer.getIntroducedDate().toString() : null)
			.discontinuedDate((computer.getDiscontinuedDate()!= null) ? computer.getDiscontinuedDate().toString() : null)
			.compId(computer.getCompId())
			.company(computer.getCompany())
			.build();
	}
}
