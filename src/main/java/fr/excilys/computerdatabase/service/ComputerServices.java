package fr.excilys.computerdatabase.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.main.Util;
import fr.excilys.computerdatabase.mapper.ComputerMapper;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.ComputerDao;
import fr.excilys.computerdatabase.servlet.ComputerDTO;
import fr.excilys.computerdatabase.validator.Validator;

public class ComputerServices {

	private static ComputerServices cs = new ComputerServices();
	
	private ComputerMapper computerMapper = ComputerMapper.getInstance();
	
	private ComputerDao computerDao;

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
		return computerMapper.computerToComputerDTO(computer);
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
		if (!Validator.isNameValid(request.getParameter("computerName"))) {
			return null;
		}
		Computer computer = computerMapper.buildComputerFromRequest(request);
		if (!computerDao.insertComputer(computer)) {
			return null;
		}
		return computer;
	}

	public Computer updateComputer(HttpServletRequest request) {
		if (!Validator.isNameValid(request.getParameter("computerName"))) {
			return null;
		}
		Computer computer = computerMapper.buildComputerFromRequest(request);
		computer.setId(Integer.valueOf(request.getParameter("id")));
		if (!computerDao.updateComputer(computer)) {
			return null;
		}
		return computer;
	}
	

	
	private List<ComputerDTO> computerDTOList(List<Computer> computers) {
		return computers.stream()
				.map(computer -> computerMapper.computerToComputerDTO(computer))
				.collect(Collectors.toList());
	}


}
