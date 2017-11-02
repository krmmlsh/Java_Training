package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.computerdatabase.mapper.ComputerMapper;
import fr.excilys.computerdatabase.model.Computer;

public class ComputerDao {

	private static final String UPDATE_COMPUTER = "UPDATE computer set name=?, introduced=?, discontinued=?, company_id=? where id=";

	private static final String INSERT_COMPUTER = "INSERT INTO computer values (NULL, ?, ?, ?, ?)";

	private static final String DELETE_FROM_ID = "DELETE FROM computer WHERE id = ";

	private static final String GET_ALL_COMPUTERS = "SELECT * FROM computer";

	private static final String COMPUTER_BY_NAME = "SELECT * FROM computer where name = ";

	private static final String GET_COMPUTER = "SELECT * FROM computer where id = ";

	private static final Logger logger = LoggerFactory.getLogger(ComputerDao.class);

	private static ComputerDao computerDao;

	private CompanyDao companyDao = CompanyDao.getInstance();

	private ComputerMapper computerMapper = ComputerMapper.getInstance();

	private ComputerDao() {

	}

	/**
	 * Get the singleton of ComputerDao.
	 * 
	 * @return ComputerDao instance
	 */
	public synchronized static ComputerDao getInstance() {
		if (computerDao == null)
			computerDao = new ComputerDao();
		return computerDao;
	}

	/**
	 * Get a computer from his id.
	 * 
	 * @param id
	 *            Id of a computer.
	 * @return A computer.
	 */
	public Computer getComputer(int id) {
		logger.trace("ENTER GET COMPUTER BY ID");

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(GET_COMPUTER + id)) {

			if (rs.next()) {

				return computerMapper.createComputerFromDatabase(rs, companyDao);
			} else {
				logger.error("Error while getting computer from : " + id + " id");
			}
		} catch (SQLException e) {
			logger.error("Error while getting computer from : " + id + " id");
		}

		return null;
	}

	/**
	 * Get a computer by name, if multiple found, return the first one in the list.
	 * 
	 * @param name
	 *            Name of the computer.
	 * @return A computer.
	 */
	public List<Computer> getComputers(String name) {
		logger.trace("ENTER GET COMPUTER BY NAME");
		List<Computer> list = new ArrayList<>();

		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(COMPUTER_BY_NAME + "'" +name+"'")) {

			while (rs.next()) {
				list.add(computerMapper.createComputerFromDatabase(rs, companyDao));
			}
		} catch (SQLException e) {
			logger.error("Error while getting computer from : " + name + " name");
		}
		return list;
	}

	/**
	 * Get all the computers from the database
	 * 
	 * @return List of Computer.
	 */
	public List<Computer> getComputers() {
		logger.trace("ENTER GET ALL COMPUTERS");

		List<Computer> list = new ArrayList<>();
		try (Connection conn = DatabaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(GET_ALL_COMPUTERS);) {
			while (rs.next()) {
				list.add(computerMapper.createComputerFromDatabase(rs, companyDao));
			}
		} catch (SQLException e) {
			logger.error("Error while getting computers from database");
		}
		return list;
	}

	/**
	 * Remove a computer from his id.
	 * 
	 * @param id
	 *            Id of the computer to remove.
	 * @return true if it worked, else false.
	 */
	public boolean removeComputer(int id) {
		logger.trace("ENTER REMOVE COMPUTER");
		try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {

			int i = stmt.executeUpdate(DELETE_FROM_ID + id);
			if (i == 1) {
				logger.debug("Computer deleted");
				return true;
			}
		} catch (SQLException e) {
			logger.error("Cannot remove computer : " + id + " name");
		}
		return false;
	}

	/**
	 * Insert a computer in the database.
	 * 
	 * @param computer
	 *            Computer to add.
	 * @return true if it worked, else false.
	 */
	public boolean insertComputer(Computer computer) {
		logger.trace("ENTER INSERT COMPUTER");

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_COMPUTER)) {

			ps.setString(1, computer.getName());
			ps.setDate(2, getDateOrNull(computer.getIntroducedDate()));
			ps.setDate(3,  getDateOrNull(computer.getDiscontinuedDate()));
			ps.setObject(4, (computer.getCompId() >= 0) ? computer.getCompId() : null);
			int i = ps.executeUpdate();

			if (i == 1) {
				logger.debug("Computer added");
				return true;
			}
		} catch (SQLException e) {
			logger.error("Computer cannot be added");
		}
		return false;
	}

	/**
	 * Update an existing computer.
	 * 
	 * @param computer
	 *            New computer details to incorporate.
	 * @return true if it worked, else false.
	 */
	public boolean updateComputer(Computer computer) {
		logger.trace("ENTER UPDATE COMPUTER");

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(UPDATE_COMPUTER + computer.getId())) {

			ps.setString(1, computer.getName());
			ps.setDate(2, getDateOrNull(computer.getIntroducedDate()));
			ps.setDate(3, getDateOrNull(computer.getDiscontinuedDate()));
			ps.setObject(4, computer.getCompId());

			int i = ps.executeUpdate();

			if (i == 1) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "Error while updating computer from database");
			logger.error(e.getMessage() + "Error while updating computer from database");
		}
		return false;
	}
	
	private Date getDateOrNull(LocalDate localdate) {
		if (localdate == null) {
			return null;
		}
		return Date.valueOf(localdate);
	}
}
