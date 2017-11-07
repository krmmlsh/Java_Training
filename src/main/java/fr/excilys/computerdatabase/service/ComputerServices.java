package fr.excilys.computerdatabase.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.ComputerDao;
import fr.excilys.computerdatabase.servlet.ComputerDTO;

public class ComputerServices {

	private static ComputerServices cs = new ComputerServices();

	private CompanyServices companyServices = CompanyServices.getCompanyServices();
	
	public ComputerDao computerDao;

	private ComputerServices() {
		computerDao = ComputerDao.getInstance();
	}

	public static ComputerServices getComputerServices() {
		return cs;
	}

	/**
	 * Get a computer from the database by his id.
	 * 
	 * @param id the {@link Computer#id} of the {@Computer}.
	 * @return The researched computer.
	 */
	public ComputerDTO getComputerById(int id) {
		Computer computer = computerDao.getComputer(id);
		return createComputerDTO(computer);
	}

	/**
	 * Get all the computers from the database.
	 * 
	 * @return a list of Computers.
	 */
	public List<ComputerDTO> getAllComputers() {
		return computerDTOList(computerDao.findAll());
	}
	

	public Object getAllComputers(int currentPage, int limit, NbTotal nbTotal) {
		return computerDTOList(computerDao.findPaging(currentPage, limit, nbTotal));
	}


	/**
	 * Get a computer by his name.
	 * 
	 * @param name Name of the computer to be found.
	 * @return A computer
	 */
	public List<ComputerDTO> getComputerByName(String name) {
		return computerDTOList(computerDao.findByName(name));
	}

	/**
	 * Create a new computer to add in the database.
	 * 
	 * @param c The computer to create.
	 */
	public void addComputer(Computer c) {
		computerDao.insertComputer(c);
	}

	/**
	 * Update a computer in the database.
	 * 
	 * @param c the computer with the new informations.
	 */
	public void updateComputer(Computer c) {
		computerDao.updateComputer(c);
	}

	/**
	 * Remove a computer with his id.
	 * 
	 * @param id The id of computer to remove.
	 */
	public boolean removeComputer(String computerIds) {
		for (String stringId : computerIds.split(",")) {
			if (computerDao.removeComputer(Integer.valueOf(stringId)) == false) {
				return false;
			}
		}
		return true;
	}

	public Computer addComputer(HttpServletRequest request) {
		Computer computer = buildComputer(request);
		computerDao.insertComputer(computer);
		return computer;
	}

	public Computer updateComputer(HttpServletRequest request) {
		Computer computer = buildComputer(request);
		computer.setId(Integer.valueOf(request.getParameter("id")));
		computerDao.updateComputer(computer);

		return computer;
	}
	
	private Computer buildComputer(HttpServletRequest request) {
		return new Computer.Builder().name(request.getParameter("computerName"))
				.compId(Integer.valueOf(request.getParameter("companyId")))
				.introducedDate(Util.convertStringToLocalDate(request.getParameter("introduced"), "dd/MM/yyyy"))
				.discontinuedDate(Util.convertStringToLocalDate(request.getParameter("discontinued"), "dd/MM/yyyy"))
				.company(companyServices.getCompany(Integer.valueOf(request.getParameter("companyId"))))
				.build();	
	}
	
	private List<ComputerDTO> computerDTOList(List<Computer> computers) {
		return computers.stream()
				.map(computer -> createComputerDTO(computer))
				.collect(Collectors.toList());
	}
	
	private ComputerDTO createComputerDTO(Computer computer) {
		return new ComputerDTO.Builder().id(computer.getId()).name(computer.getName())
			.introducedDate((computer.getIntroducedDate()!= null) ? computer.getIntroducedDate().toString() : null)
			.discontinuedDate((computer.getDiscontinuedDate()!= null) ? computer.getDiscontinuedDate().toString() : null)
			.compId(computer.getCompId())
			.company(computer.getCompany())
			.build();
	}

}
