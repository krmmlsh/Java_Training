package fr.excilys.computerdatabase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import fr.excilys.computerdatabase.model.Computer;
import fr.excilys.computerdatabase.persistence.ComputerDao;

@RunWith(MockitoJUnitRunner.class)
public class ServiceComputerTests {

	@InjectMocks
	public ComputerServices cs;

	@Mock
	public ComputerDao computerDao;

	@Test
	public void getAllComputersTest_SUCCESS() {
		List<Computer> computers = new ArrayList<>();
		computers.add(new Computer());
		computers.add(new Computer());

		Mockito.when(computerDao.getComputers()).thenReturn(computers);
		assertEquals(cs.getAllComputers().size(), 2);
	}

	@Test
	public void getComputerById_SUCCESS() {
		Computer c = new Computer("Karim", -1);
		c.setId(0);
		Mockito.when(computerDao.getComputer(0)).thenReturn(c);
		assertEquals(c, computerDao.getComputer(0));
	}

	@Test
	public void addOne_SUCCESS() {
		Mockito.when(computerDao.getComputer("")).thenReturn(new Computer("Karim", -1));

		assertEquals(computerDao.getComputer("").getName(), "Karim");
	}

	@Test
	public void updateOne_SUCCESS() {
		Computer c = new Computer("Karim", -1);
		c.setName("Oezdemir");
		Mockito.when(computerDao.updateComputer(c)).thenReturn(true);
		assertEquals(true, computerDao.updateComputer(c));
	}

	@Test
	public void remove_SUCCESS() {
		Computer c = new Computer("Karim", -1);
		c.setId(0);
		Mockito.when(computerDao.removeComputer(0)).thenReturn(true);
		assertEquals(true, computerDao.removeComputer(0));
	}

}
