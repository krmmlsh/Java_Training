//package fr.excilys.computerdatabase.mapper;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
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
//
//@RunWith(MockitoJUnitRunner.class)
//public class ComputerMapperTest {
//
//	@InjectMocks
//	public ComputerMapper computerMapper;
//
//	@Mock
//	public ResultSet resultSet;
//	
//	@Test
//	public void createComputer_SUCCES() throws SQLException {
//		Mockito.when(resultSet.getInt("id")).thenReturn(0);
//		Mockito.when(resultSet.getString("name")).thenReturn("karim");
//		Mockito.when(resultSet.getInt("company_id")).thenReturn(5);
//		Mockito.when(resultSet.getString("introduced")).thenReturn("2017-04-04");
//		Mockito.when(resultSet.getString("discontinued")).thenReturn("2000-01-01");
//		assertEquals(computerMapper.createComputerFromDatabase(resultSet), new Computer("karim"
//				, 5
//				, Util.convertStringToLocalDate("2017-04-04", "yyyy-MM-dd")
//				, Util.convertStringToLocalDate("2000-01-01", "yyyy-MM-dd")));
//	}
//	
//	@Test
//	public void createComputer_FAILURE() throws SQLException {
//		Mockito.when(resultSet.getInt("id")).thenReturn(1);
//		Mockito.when(resultSet.getString("name")).thenReturn("karim");
//		Mockito.when(resultSet.getInt("company_id")).thenReturn(5);
//		Mockito.when(resultSet.getString("introduced")).thenReturn("2017-04-04");
//		Mockito.when(resultSet.getString("discontinued")).thenReturn("2000-01-01");
//		assertNotEquals(computerMapper.createComputerFromDatabase(resultSet), new Computer("karim"
//				, 5
//				, Util.convertStringToLocalDate("2017-04-04", "yyyy-MM-dd")
//				, Util.convertStringToLocalDate("2000-01-01", "yyyy-MM-dd")));
//	}
//	
//}
