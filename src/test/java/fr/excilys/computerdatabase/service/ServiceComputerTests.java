//package fr.excilys.computerdatabase.service;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import fr.excilys.computerdatabase.main.Util;
//import fr.excilys.computerdatabase.model.Computer;
//import fr.excilys.computerdatabase.persistence.ComputerDaoImpl;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ServiceComputerTests {
//
//	@InjectMocks
//	public ComputerServices cs;
//
//	@Mock
//	public ComputerDaoImpl computerDao;
//	
//	@Mock
//	public HttpServletRequest request;
//
//	@Test
//	public void getAllComputersTest_SUCCESS() {
//		List<Computer> computers = new ArrayList<>();
//		computers.add(new Computer());
//		computers.add(new Computer());
//
//	//	Mockito.when(computerDao.findAll()).thenReturn(computers);
//		//assertEquals(cs.getAllComputers().size(), 2);
//	}
//
//	@Test
//	public void getComputerById_SUCCESS() {
//		Computer c = new Computer("Karim", -1);
//		c.setId(0);
//		Mockito.when(computerDao.getComputer(0)).thenReturn(c);
//		assertEquals(c, computerDao.getComputer(0));
//	}/*
//
//	@Test
//	public void addOne_SUCCESS() {
//		Computer c = new Computer'.Builder().name("karim")
//				.compId(Integer.valueOf("5"))
//				.introducedDate(Util.convertStringToLocalDate("04/04/2017","dd/MM/yyyy"))
//				.discontinuedDate(Util.convertStringToLocalDate("01/01/2000", "dd/MM/yyyy"))
//				.build();
//		Mockito.when(request.getParameter("computerName")).thenReturn("karim");
//		Mockito.when(request.getParameter("companyId")).thenReturn("5");
//		Mockito.when(request.getParameter("introduced")).thenReturn("2017-04-04");
//		Mockito.when(request.getParameter("discontinued")).thenReturn("2000-01-01");
//		
//
//		assertEquals(c, cs.addComputer(request));
//	}
//
//	@Test
//	public void updateOne_SUCCESS() {
//		Computer c = new Computer.Builder().name("karim")
//				.compId(Integer.valueOf("5"))
//				.introducedDate(Util.convertStringToLocalDate("04/04/2017","dd/MM/yyyy"))
//				.discontinuedDate(Util.convertStringToLocalDate("01/01/2000", "dd/MM/yyyy"))
//				.build();
//		Mockito.when(request.getParameter("id")).thenReturn("0");
//		Mockito.when(request.getParameter("computerName")).thenReturn("karim");
//		Mockito.when(request.getParameter("companyId")).thenReturn("5");
//		Mockito.when(request.getParameter("introduced")).thenReturn("2017-04-04");
//		Mockito.when(request.getParameter("discontinued")).thenReturn("2000-01-01");
//		
//		Mockito.when(computerDao.updateComputer(c)).thenReturn(true);
//		assertEquals(c, cs.updateComputer(request));
//	}
//*/
//
//	@Test
//	public void removeByString_SUCCESS() {
//		Mockito.when(computerDao.removeComputer(1)).thenReturn(true);
//		Mockito.when(computerDao.removeComputer(2)).thenReturn(true);
//		Mockito.when(computerDao.removeComputer(3)).thenReturn(true);
//		assertEquals(true, cs.removeComputer("1,2,3"));
//	}
//}
