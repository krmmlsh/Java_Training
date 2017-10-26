package fr.excilys.computerdatabase.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest {

	Company company;
	
	@Before
	public void initialyser() {
		company = new Company();
	}
	
	@Test
	public void testCompany() {
		company.setId(1);
		company.setName("Karim");
		assertEquals("Karim", company.getName());
		assertEquals(1, company.getId());
	}
	
}
