package fr.excilys.computerdatabase.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


/**
 * Link between java code and compagny table
 * @author krmmlsh
 *
 */
public class CompanyDao {

	private final static String getAllCompanies ="SELECT * FROM company"; 
	
	private static final Map<Integer, String> companies;

	private static CompanyDao companyDao;
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	private static DatabaseConnection databaseConnection = DatabaseConnection.getInstance("/hikari.properties");
	
	private CompanyDao() {
		
	}
	
	public synchronized static CompanyDao getInstance() {
		if (companyDao == null)
			companyDao = new CompanyDao();
		return companyDao;
	}
	
	/**
	 * Since we can't add anything to the company table and it is rather short.
	 * At the first call of this class, we store the whole table locally to improve performances.
	 */
	static {
		logger.info("STORAGE OF ALL COMPANIES START");
		companies = new HashMap<>();
		try (Connection conn = databaseConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getAllCompanies);) {

			while (rs.next()) {
				companies.put(rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("Error on companies storage");
		}
	}

	/**
	 * Find company id by its name.
	 * @param name name of the company
	 * @return id of the company or -1 if it doesn't exist.
	 */
	public int getCompany(String name) {
		// look in all the values of the map to find a company with this name
		Optional<Entry<Integer, String>> entryO = companies
				.entrySet()
				.stream()
				.filter(e -> e.getValue().equals(name))
				.findFirst();
		// At least one company has this name
		if(entryO.isPresent()) {
			Entry<Integer, String> e = entryO.get();
			if ( e == null) {
				return -1;
			}
			return e.getKey();
		}
		return -1;
	}

	/**
	 * Name of a company 
	 * @param id id of a company
	 * @return company's name
	 * @throws SQLException
	 */
	public String getCompany(int id) {
		return companies.get(id);
	}

	/**
	 * Get all the companies
	 * @return map of companies
	 */
	public Map<Integer, String> getCompanies() {
		return companies;
	}

}
