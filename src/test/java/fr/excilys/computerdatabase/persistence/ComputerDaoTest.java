package fr.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ibatis.common.jdbc.ScriptRunner;

import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.ComputerDao;
import fr.excilys.computerdatabase.persistence.DatabaseConnexion;
import fr.excilys.computerdatabase.service.ComputerServices;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDaoTest {

	@InjectMocks
	public ComputerServices cs;
	
	@Mock
	public ComputerDao computerDao;
	
	private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
//		cs = ComputerServices.getComputerServices();
	    if (setUpIsDone) {
	        return;
	    }
		String s[] = DatabaseConnexion.url.split("computer-database-db");
		DatabaseConnexion.url = s[0] + "computer-database-db-test" + s[1];
	    setUpIsDone = true;
	}
	
	
	
	@After
	public void destroyEverything() {
    	emptyDatabase();
	}
	
	private void emptyDatabase() {
		String sqlDrop ="config/db/dropTable.sql";
		Connection con = DatabaseConnexion.getConnection();
		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(con, false, false);

			// Give the input file to Reader
			Reader reader = new BufferedReader(
                               new FileReader(sqlDrop));

			// Exctute script
			sr.runScript(reader);

		} catch (Exception e) {
			System.err.println("Failed to Execute" + sqlDrop
					+ " The error is " + e.getMessage());
		}
	}
	
	@Test
	public void getNothing() {
		List<Computer> computers = cs.getAllComputers();
		assertEquals(0, computers.size());
	}
	
	@Test
	public void getAllComputersTest_SUCCESS() {
		List<Computer> computers = new ArrayList<>();
		computers.add(new Computer());
		computers.add(new Computer());

		Mockito.when(computerDao.getComputers()).thenReturn(computers);
		assertEquals(cs.getAllComputers().size(), 2);
	}
	@Test
	public void addOne() {
		cs.addComputer(new Computer("Karim",-1));
		List<Computer> computers = cs.getAllComputers();
		assertEquals(1, computers.size());
	}
	
	@Test
	public void updateOne() {
		cs.addComputer(new Computer("Karim",-1));
		Computer c = cs.getComputerByName("Karim");
		c.setName("Oezdemir");
		List<Computer> computers = cs.getAllComputers();
		assertEquals(1, computers.size());
	}
	
	
}