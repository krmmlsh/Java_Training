package fr.excilys.computerdatabase.model;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

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
		computer.setDiscontinuedDate(new Timestamp(1,1,1,1,1,1,1));
		computer.setIntroducedDate(new Timestamp(0,1,1,1,1,1,1));
		computer.setName("Karim");
		computer.setId(10);
		assertEquals("Nintendo", computer.getCompany());
		assertEquals(24, computer.getCompId());
		assertEquals(new Timestamp(1,1,1,1,1,1,1), computer.getDiscontinuedDate());
		assertEquals(new Timestamp(0,1,1,1,1,1,1), computer.getIntroducedDate());
		assertEquals("Karim", computer.getName());
		assertEquals(10, computer.getId());

	}
	
}
