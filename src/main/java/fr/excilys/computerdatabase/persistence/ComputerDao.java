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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.mapper.ComputerMapper;
import fr.excilys.computerdatabase.model.Computer;


@Component
public class ComputerDao {

	private static final String UPDATE_COMPUTER = "UPDATE computer set name=?, introduced=?, discontinued=?, company_id=? where id=";

	private static final String INSERT_COMPUTER = "INSERT INTO computer values (NULL, ?, ?, ?, ?)";

	private static final String DELETE_FROM_ID = "DELETE FROM computer WHERE id = ";

	private static final String DELETE_FROM_COMPANY_ID = "DELETE FROM computer WHERE company_id = ";

	private static final String GET_ALL_COMPUTERS = "SELECT SQL_CALC_FOUND_ROWS computer.id as id, computer.name as name, computer.company_id as company_id, "
			+ "computer.introduced, computer.discontinued , company.name AS comName FROM computer computer LEFT JOIN company company on computer.company_id = company.id";

	private static final String COMPUTER_BY_NAME = "SELECT computer.id as id, computer.name as name, computer.company_id as company_id, "
			+ "computer.introduced, computer.discontinued , company.name AS comName FROM computer computer LEFT JOIN company company ON computer.company_id = company.id where computer.name LIKE ? OR (company.name LIKE ?)";

	private static final String GET_COMPUTER = "SELECT computer.id as id, computer.name as name, computer.company_id as company_id, "
			+ "computer.introduced, computer.discontinued , company.name AS comName FROM computer computer LEFT JOIN company company ON computer.company_id = company.id where computer.id = ";

	private static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
	@Autowired
	private ComputerMapper computerMapper;

	@Autowired
	private DatabaseConnection databaseConnection;


	/**
	 * Get a computer from his id.
	 * 
	 * @param id
	 *            Id of a computer.
	 * @return A computer.
	 */
	public Computer getComputer(int id) {
		logger.trace("ENTER GET COMPUTER BY ID");

		try (Connection conn = databaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(GET_COMPUTER + id)) {

			if (rs.next()) {

				return computerMapper.createComputerFromDatabase(rs);
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
	public List<Computer> findByName(String name) {
		logger.trace("ENTER GET COMPUTER BY NAME");
		List<Computer> list = new ArrayList<>();

		try (Connection conn = databaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(COMPUTER_BY_NAME);) {
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(computerMapper.createComputerFromDatabase(rs));
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage() + " error in get Page");

			logger.error("Error while getting computer from : " + name + " name");
		}
		return list;
	}

	/**
	 * Get all the computers from the database
	 * 
	 * @return List of Computer.
	 */
	public List<Computer> findAll() {
		logger.trace("ENTER GET ALL COMPUTERS");

		List<Computer> list = new ArrayList<>();
		try (Connection conn = databaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(GET_ALL_COMPUTERS);) {
			while (rs.next()) {
				list.add(computerMapper.createComputerFromDatabase(rs));
			}
		} catch (SQLException e) {
			logger.error("Error while getting computers from database");
		}
		return list;
	}

	public List<Computer> findPaging(int currentPage, int limit, NbTotal nbTotal) {
		logger.trace("ENTER GET ALL COMPUTERS");

		List<Computer> list = new ArrayList<>();
		try (Connection conn = databaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt
						.executeQuery(GET_ALL_COMPUTERS + " limit " + limit + " offset " + (currentPage) * limit);) {
			while (rs.next()) {
				list.add(computerMapper.createComputerFromDatabase(rs));
			}
			try (ResultSet resultTotal = stmt.executeQuery("SELECT FOUND_ROWS()");) {
				if (resultTotal.next()) {
					nbTotal.nomberOfComputer = resultTotal.getInt(1);
				}
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
		try (Connection conn = databaseConnection.getConnection(); Statement stmt = conn.createStatement()) {

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

		try (Connection conn = databaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_COMPUTER)) {

			ps.setString(1, computer.getName());
			ps.setDate(2, getDateOrNull(computer.getIntroducedDate()));
			ps.setDate(3, getDateOrNull(computer.getDiscontinuedDate()));
			ps.setObject(4, (computer.getCompId() >= 0) ? computer.getCompId() : null);
			int i = ps.executeUpdate();

			if (i == 1) {
				logger.debug("Computer added");
				return true;
			}
		} catch (SQLException e) {
			logger.error(e.getMessage() + " Computer cannot be added");
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

		try (Connection conn = databaseConnection.getConnection();
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
			logger.error(e.getMessage() + "Error while updating computer from database");
		}
		return false;
	}

	public boolean deleteComputerFromCompany(int id, Connection conn) {
		try (Statement stmt = conn.createStatement()) {
			int i = stmt.executeUpdate(DELETE_FROM_COMPANY_ID + id);
			if (i >= 0) {
				logger.debug("Computer deleted");
				return true;
			}
		} catch (SQLException e) {
			logger.error("Cannot remove computer : " + id + " name");
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
