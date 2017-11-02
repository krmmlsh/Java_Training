package fr.excilys.computerdatabase.service;

import java.util.List;

import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.ComputerDao;

public class ComputerServices {

	private static ComputerServices cs = new ComputerServices();
	
	public ComputerDao computerDao;
	public ComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}

	private ComputerServices () {
		computerDao = ComputerDao.getInstance();
	}
	
	public static ComputerServices getComputerServices() {
		return cs;
	}
	
	/**
	 * Get a computer from the database by his id.
	 * @param id : Id of the computer.
	 * @return The researched computer.
	 */
	public Computer getComputerById(int id) {
		return computerDao.getComputer(id);
	}
	
	/**
	 * Get all the computers from the database.
	 * @return a list of Computers.
	 */
	public List<Computer> getAllComputers() {
		return computerDao.getComputers();
	}
	
	/**
	 * Get a computer by his name.
	 * @param name : Name of the computer to be found.
	 * @return A computer
	 */
	public List<Computer> getComputerByName(String name) {
		return computerDao.getComputers(name);
	}
	
	/**
	 * Create a new computer to add in the database.
	 * @param c : The computer to create.
	 */
	public void addComputer(Computer c) {
		computerDao.insertComputer(c);
	}
	
	/**
	 * Update a computer in the database.
	 * @param c : the computer with the new informations.
	 */
	public void updateComputer(Computer c) {
		computerDao.updateComputer(c);
	}
	
	/**
	 * Remove a computer with his id.
	 * @param id : The id of computer to remove.
	 */
	public void removeComputer(String computerIds) {
		for(String computerId : computerIds.split(",")) {
			computerDao.removeComputer(Integer.valueOf(computerId));
		}
	}
	
}
