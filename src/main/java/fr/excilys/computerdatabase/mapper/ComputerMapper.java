package fr.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.servlet.ComputerDTO;

@Component
public class ComputerMapper {


	/**
	 * Fill computer with ResultSet values.
	 * 
	 * @param rs
	 *            ResultSet from the select query.
	 * @return Computer newly filled.
	 * @throws SQLException
	 */
	public Computer createComputerFromDatabase(ResultSet rs) throws SQLException {
		Computer computer = new Computer();
		computer.setId(rs.getInt("id"));
		computer.setName(rs.getString("name"));
		computer.setCompany(rs.getString("comName"));
		computer.setIntroducedDate(Util.convertStringToLocalDate(rs.getString("introduced"), "yyyy-MM-dd"));
		computer.setDiscontinuedDate(Util.convertStringToLocalDate(rs.getString("discontinued"), "yyyy-MM-dd"));
		return computer;
	}

	public Computer buildComputerFromRequest(HttpServletRequest request) {
		Computer computer = new Computer();
		computer.setName(request.getParameter("computerName"));
		computer.setCompId(Integer.valueOf(request.getParameter("companyId")));
		computer.setIntroducedDate(Util.convertStringToLocalDate(request.getParameter("introduced"), "dd/MM/yyyy"));
		computer.setDiscontinuedDate(Util.convertStringToLocalDate(request.getParameter("discontinued"), "dd/MM/yyyy"));
		return computer;
	}

	public ComputerDTO computerToComputerDTO(Computer computer) {
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
				.introducedDate((computer.getIntroducedDate() != null) ? computer.getIntroducedDate().toString() : null)
				.discontinuedDate(
						(computer.getDiscontinuedDate() != null) ? computer.getDiscontinuedDate().toString() : null)
				.compId(computer.getCompId()).company(computer.getCompany()).build();
	}
	
	public Computer computerDTOtoComputer(ComputerDTO computerDTO) {
		return new Computer.Builder().id(computerDTO.getId()).name(computerDTO.getName())
				.introducedDate((computerDTO.getIntroduced() != null) ? Util.convertStringToLocalDate(computerDTO.getIntroduced().toString(), "dd/MM/yyyy") : null)
				.discontinuedDate(
						(computerDTO.getDiscontinued() != null) ? Util.convertStringToLocalDate(computerDTO.getDiscontinued().toString(), "dd/MM/yyyy") : null)
				.compId(computerDTO.getCompanyId()).company(computerDTO.getCompany()).build();
	}
}
