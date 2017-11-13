package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.mapper.CompanyMapper;
import fr.excilys.computerdatabase.model.Company;

/**
 * Link between java code and compagny table
 * 
 * @author krmmlsh
 *
 */
@Component
public class CompanyDao {

	private final static String getAllCompanies = "SELECT * FROM company";

	private final static String getCompany = "SELECT * FROM company where id = ?";

	private final static String getCompanyByName = "SELECT * FROM company where name = ?";

	private final static String DELETE_FROM_ID = "DELETE FROM company WHERE id = ";

	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	private static DatabaseConnection databaseConnection = DatabaseConnection.getInstance("/hikari.properties");

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private ComputerDao computerDao;


	/**
	 * Find company id by its name.
	 * 
	 * @param name
	 *            name of the company
	 * @return id of the company or -1 if it doesn't exist.
	 */
	public Company getCompanyByName(String name) {
		try (Connection conn = databaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getCompanyByName);) {
			stmt.setString(1, name);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return companyMapper.createCompanyFromDatabase(rs);
				}
			}
		} catch (SQLException e) {
			logger.error("Error on companies storage");
		}
		return null;
	}

	/**
	 * Name of a company
	 * 
	 * @param id
	 *            id of a company
	 * @return company's name
	 * @throws SQLException
	 */
	public Company getCompany(int id) {
		try (Connection conn = databaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(getCompany);) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return companyMapper.createCompanyFromDatabase(rs);
				}
			}
		} catch (SQLException e) {
			logger.error("Error on companies storage");
		}
		return null;
	}

	/**
	 * Get all the companies
	 * 
	 * @return map of companies
	 */
	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<>();
		try (Connection conn = databaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getAllCompanies);) {

			while (rs.next()) {
				companies.add(companyMapper.createCompanyFromDatabase(rs));
			}
		} catch (SQLException e) {
			logger.error("Error on companies storage");
		}
		return companies;
	}

	public boolean deleteCompany(int id) {
		Connection conn = null;
		try {
			conn = databaseConnection.getConnection();
			conn.setAutoCommit(false);
			if (computerDao.deleteComputerFromCompany(id, conn)) {

				try (Statement stmt = conn.createStatement()) {
					int i = stmt.executeUpdate(DELETE_FROM_ID + id);
					if (i > 0) {
						logger.debug("Company deleted");
						conn.setAutoCommit(true);
						return true;
					}
				}
			} else {
				conn.rollback();
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				logger.error(ex.getMessage() + "Cannot remove company : " + id + " name");

			}
			logger.error(e.getMessage() +  "Cannot remove company : " + id + " name");

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.error(e.getMessage() + " Error while closing connection");

			}
		}
		return false;
	}

}
