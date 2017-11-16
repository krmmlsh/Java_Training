package fr.excilys.computerdatabase.persistence;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.main.Util;
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
	private HikariDataSource databaseConnection;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * Get a computer from his id.
	 * 
	 * @param id
	 *            Id of a computer.
	 * @return A computer.
	 */
	public Computer getComputer(int id) {
		logger.trace("ENTER GET COMPUTER BY ID");
		return (Computer) jdbcTemplate.queryForObject(GET_COMPUTER + id, new ComputerRowMapper());
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
		List<Computer> computers = jdbcTemplate.query(COMPUTER_BY_NAME,
				new Object[] { "%" + name + "%", "%" + name + "%" },  new ComputerRowMapper());
		return computers;
	}

	/**
	 * Get all the computers from the database
	 * 
	 * @return List of Computer.
	 */
	public List<Computer> findAll() {
		logger.trace("ENTER GET ALL COMPUTERS");
		List<Computer> computers = jdbcTemplate.query(GET_ALL_COMPUTERS,  new ComputerRowMapper());

		return computers;
	}

	public List<Computer> findPaging(int currentPage, int limit, NbTotal nbTotal) {
		logger.trace("ENTER GET ALL COMPUTERS");
		List<Computer> list = jdbcTemplate.query(
				GET_ALL_COMPUTERS + " limit " + limit + " offset " + (currentPage) * limit,
				 new ComputerRowMapper());
		nbTotal.nomberOfComputer = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()", Integer.class);
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
		jdbcTemplate.update(DELETE_FROM_ID + id);
		return true;
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

		jdbcTemplate.update(INSERT_COMPUTER, computer.getName(), getDateOrNull(computer.getIntroducedDate()),
				getDateOrNull(computer.getDiscontinuedDate()),
				(computer.getCompId() >= 0) ? computer.getCompId() : null);
		return true;
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

		jdbcTemplate.update(UPDATE_COMPUTER, computer.getName(), getDateOrNull(computer.getIntroducedDate()),
				getDateOrNull(computer.getDiscontinuedDate()), computer.getCompId());
		return true;
	}

	public boolean deleteComputerFromCompany(int id) {
		try {
			jdbcTemplate.update(DELETE_FROM_COMPANY_ID + id);
		} catch (DataAccessException e) {
			return false;
		}
		return true;
	}

	private Date getDateOrNull(LocalDate localdate) {
		if (localdate == null) {
			return null;
		}
		return Date.valueOf(localdate);
	}

	class ComputerRowMapper implements RowMapper<Computer> {
		public Computer mapRow(ResultSet rs, int nbRow) throws SQLException {
			Computer computer = new Computer();
			computer.setId(rs.getInt("id"));
			computer.setName(rs.getString("name"));
			computer.setCompany(rs.getString("comName"));
			computer.setIntroducedDate(Util.convertStringToLocalDate(rs.getString("introduced"), "yyyy-MM-dd"));
			computer.setDiscontinuedDate(Util.convertStringToLocalDate(rs.getString("discontinued"), "yyyy-MM-dd"));
			return computer;
		}
	}

}
