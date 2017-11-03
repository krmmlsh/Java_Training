package fr.excilys.computerdatabase.model;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import fr.excilys.computerdatabase.main.Util;

public class ComputerTest {

	
	Computer computer;
	
	@Before
	public void initialyse () {
		computer = new Computer();
	}
	
	@Test
	public void completeTest() {
		computer.setCompany("Nintendo");
		computer.setCompId(24);
		computer.setDiscontinuedDate(Util.convertStringToLocalDate("30-04-1994", "dd-MM-yyyy"));
		computer.setIntroducedDate(Util.convertStringToLocalDate("30-04-1994", "dd-MM-yyyy"));
		computer.setName("Karim");
		computer.setId(10);
		assertEquals("Nintendo", computer.getCompany());
		assertEquals(24, computer.getCompId());
		assertEquals(Util.convertStringToLocalDate("30-04-1994", "dd-MM-yyyy"), computer.getDiscontinuedDate());
		assertEquals(Util.convertStringToLocalDate("30-04-1994", "dd-MM-yyyy"), computer.getIntroducedDate());
		assertEquals("Karim", computer.getName());
		assertEquals(10, computer.getId());

	}
	
}
