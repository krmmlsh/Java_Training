package fr.excilys.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.zaxxer.hikari.HikariDataSource;

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

	@Autowired
	private HikariDataSource databaseConnection;

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private ComputerDao computerDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PlatformTransactionManager transactionManager;

	/**
	 * Find company id by its name.
	 * 
	 * @param name
	 *            name of the company
	 * @return id of the company or -1 if it doesn't exist.
	 */
	public Company getCompanyByName(String name) {
		return (Company) jdbcTemplate.queryForObject(getCompanyByName, new Object[] { name }, new CompanyRowMapper());
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
		return (Company) jdbcTemplate.queryForObject(getCompany, new Object[] { id }, new CompanyRowMapper());
	}

	/**
	 * Get all the companies
	 * 
	 * @return map of companies
	 */
	public List<Company> getCompanies() {
		return jdbcTemplate.query(getAllCompanies, new CompanyRowMapper());
	}

	public boolean deleteCompany(int id) {
		TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			if (computerDao.deleteComputerFromCompany(id)) {

				jdbcTemplate.update(DELETE_FROM_ID + id);
				transactionManager.commit(ts);
				return true;

			}
		} catch (DataAccessException e) {
			transactionManager.rollback(ts);
			return false;
		}

		transactionManager.rollback(ts);
		return false;
	}

	class CompanyRowMapper implements RowMapper<Company> {
		public Company mapRow(ResultSet rs, int nbRow) throws SQLException {
			Company company = new Company();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
			return company;
		}
	}

}
