package fr.excilys.computerdatabase.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.mapper.ComputerMapper;
import fr.excilys.computerdatabase.model.Company;
import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.model.ComputerDTO;
import fr.excilys.computerdatabase.model.User;
import fr.excilys.computerdatabase.persistence.ComputerDaoImpl;

@Component
public class ComputerServices {

	@Autowired
	private ComputerMapper computerMapper;

	@Autowired
	private ComputerDaoImpl computerDao;

	/**
	 * Get a computer from the database by his id.
	 * 
	 * @param id
	 *            the {@link Computer#id} of the {@Computer}.
	 * @return The researched computer.
	 */
	public ComputerDTO getComputerById(int id) {
		Computer computer = computerDao.getComputer(id);
		if (computer != null) {
			return computerMapper.computerToComputerDTO(computer);
		}
		return null;
	}

	public List<ComputerDTO> getAllComputers(int currentPage, int limit, NbTotal nbTotal) {
		return computerDTOList(computerDao.findPaging(currentPage, limit, nbTotal));
	}

	/**
	 * Get a computer by his name.
	 * 
	 * @param name
	 *            Name of the computer to be found.
	 * @return A computer
	 */
	public List<ComputerDTO> getComputerByName(String name) {
		return computerDTOList(computerDao.findByName(name));
	}

	/**
	 * Create a new computer to add in the database.
	 * 
	 * @param c
	 *            The computer to create.
	 */
	public void addComputer(Computer c) {
		computerDao.insertComputer(c);
	}

	/**
	 * Update a computer in the database.
	 * 
	 * @param c
	 *            the computer with the new informations.
	 */
	public void updateComputer(Computer c) {
		computerDao.updateComputer(c);
	}

	/**
	 * Remove a computer with his id.
	 * 
	 * @param id
	 *            The id of computer to remove.
	 */
	public boolean removeComputer(String computerIds) {
		for (String stringId : computerIds.split(",")) {
			if (computerDao.removeComputer(Integer.valueOf(stringId)) == false) {
				return false;
			}
		}
		return true;
	}

	private List<ComputerDTO> computerDTOList(List<Computer> computers) {
		return computers.stream().map(computer -> computerMapper.computerToComputerDTO(computer))
				.collect(Collectors.toList());
	}

//	public boolean addComputer(ComputerDTO computerDTO, List<Company> companies) {
//		return computerDao.insertComputer(computerMapper.computerDTOtoComputer(computerDTO, companies));
//	}
	
	public boolean addComputer(ComputerDTO computerDTO, List<Company> companies, User user) {
		return computerDao.insertComputer(computerMapper.computerDTOtoComputer(computerDTO, companies, user));
	}

	public boolean updateComputer(ComputerDTO computerDTO, List<Company> companies, User user) {
		return computerDao.updateComputer(computerMapper.computerDTOtoComputer(computerDTO, companies, user));
	}

}
