package fr.excilys.computerdatabase.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.model.Company;

/**
 * Link between java code and compagny table
 * 
 * @author krmmlsh
 *
 */
@Component
public class CompanyDaoImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

	@Autowired
	private ComputerDaoImpl computerDao;

	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * Find company id by its name.
	 * 
	 * @param name
	 *            name of the company
	 * @return id of the company or -1 if it doesn't exist.
	 */
	public Company getCompanyByName(String name) {
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(Company.class);
			Criterion companyName = Restrictions.like("name", "%"+name+"%");
			cr.add(companyName);
			Company company = (Company)cr.uniqueResult();
			return company;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return null;
	}
	
	

	public Company createCompany(String companyName) {
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			Company company = new Company();
			company.setName(companyName);
			session.persist(company);
			tx.commit();
			return company;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return new Company();
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
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(Company.class);
			cr.add(Restrictions.eq("id", id));
			Company company = (Company) cr.uniqueResult();
			return company;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return null;	
	}

	/**
	 * Get all the companies
	 * 
	 * @return map of companies
	 */
	public List<Company> getCompanies() {
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(Company.class);
			List<Company> companies = cr.list();
			return companies;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return null;	
	}

	public boolean deleteCompany(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (computerDao.deleteComputerFromCompany(id)) {
				Company company = new Company();
				company.setId(id);
				session.delete(company);
				tx.commit();
				return true;
			}
		} catch (HibernateException he) {
			tx.rollback();
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
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
