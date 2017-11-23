package fr.excilys.computerdatabase.persistence;

import java.util.List;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.model.Computer;

public interface ComputerDao {

	public Computer getComputer(int id);
	public List<Computer> findByName(String name);
	public List<Computer> findPaging(int currentPage, int limit, NbTotal nbTotal);
	public boolean removeComputer(int id);
	public boolean insertComputer(Computer computer);
	public boolean updateComputer(Computer computer);
	public boolean deleteComputerFromCompany(int id);
	
	
	
}
