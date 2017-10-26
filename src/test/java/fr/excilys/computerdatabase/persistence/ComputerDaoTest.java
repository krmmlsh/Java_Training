package fr.excilys.computerdatabase.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.ibatis.common.jdbc.ScriptRunner;

import fr.excilys.computerdatabase.model.Computer;

@RunWith(MockitoJUnitRunner.class)
public class ComputerDaoTest {

	private ComputerDao computerDao = ComputerDao.getInstance();
	
	private static boolean setUpIsDone = false;
	
	@Before
	public void setUp() {
	    if (setUpIsDone) {
	    	databaseRequest("config/db/createComputers.sql");
	        return;
	    }
		String s[] = DatabaseConnexion.url.split("computer-database-db");
		DatabaseConnexion.url = s[0] + "computer-database-db-test" + s[1];
    	databaseRequest("config/db/createComputers.sql");

	    setUpIsDone = true;
	}

	@After
	public void destroyEverything() {
    	databaseRequest("config/db/dropTable.sql");
	}
	
	private void databaseRequest(String file) {
		Connection con = DatabaseConnexion.getConnection();
		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(con, false, false);

			// Give the input file to Reader
			Reader reader = new BufferedReader(
                               new FileReader(file));

			// Exctute script
			sr.runScript(reader);
			
		} catch (Exception e) {
			System.err.println("Failed to Execute" + file
					+ " The error is " + e.getMessage());
		}
	}
	

	@Test
	public void getAllComputersTest_SUCCESS() {
		assertEquals(computerDao.getComputers().size(), 3);
	}
	
	@Test
	public void getAllComputersTest_FAILURE() {
		assertNotEquals(computerDao.getComputers().size(), 4);
	}
	@Test
	public void addOne_SUCCESS() {
		assertEquals(true, computerDao.insertComputer(new Computer("Karim",-1)));
	}
	
	
	@Test
	public void addOne_FAILURE() {
		assertEquals(false, computerDao.insertComputer(new Computer("Karim", 100)));
	}
	@Test
	public void updateOne_SUCCESS() {
		Computer c = new Computer("MacBook Pro 15.4 inch","Apple",null,null);
		c.setId(1);
		c.setCompId(1);
		c.setName("KarimMacbookPro");
		assertEquals(true,	computerDao.updateComputer(c));
	}
	
	@Test
	public void updateOne_FAILURE() {
		Computer c = new Computer("MacBook Pro 15.4 inch","Apple",null,null);
		c.setId(-2);
		c.setCompId(1);
		c.setName("KarimMacbookPro");
		assertEquals(false,	computerDao.updateComputer(c));
	}
	
	@Test
	public void removeComputer_SUCCESS() {
		assertEquals(true,	computerDao.removeComputer(1));

	}
	
	@Test
	public void removeComputer_FAILURE() {
		assertEquals(false,	computerDao.removeComputer(-1));

	}
	
	
}