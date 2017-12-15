package fr.excilys.computerdatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.main.NbTotal;
import fr.excilys.computerdatabase.mapper.ComputerMapper;
import fr.excilys.computerdatabase.model.Computer;

@Component
public class ComputerDaoImpl implements ComputerDao {

	private static final String DELETE_FROM_ID = "DELETE FROM Computer WHERE id = :id";

	private static final String DELETE_FROM_COMPANY_ID = "DELETE FROM Computer WHERE company_id = :id";

	private static Logger logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ComputerMapper computerMapper;

	/**
	 * Get a computer from his id.
	 * 
	 * @param id
	 *            Id of a computer.
	 * @return A computer.
	 */
	public Computer getComputer(int id) {
		logger.trace("ENTER GET COMPUTER BY ID");
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(Computer.class);
			cr.add(Restrictions.eq("id", id));
			Computer computer = (Computer) cr.uniqueResult();
			return computer;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
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
	public List<Computer> findByName(String name, NbTotal nbTotal) {
		logger.trace("ENTER GET COMPUTER BY ID");
		Session session = sessionFactory.openSession();

		try {
			Criteria cr = session.createCriteria(Computer.class, "computer");
			cr.createAlias("computer.company", "company");
			Criterion computerName = Restrictions.like("name", "%" + name + "%");
			Criterion companyName = Restrictions.like("company.name", "%" + name + "%");
			LogicalExpression le = Restrictions.or(computerName, companyName);
			cr.add(le);
			List<Computer> computers = cr.list();
			nbTotal.nomberOfComputer = computers.size();
			return computers;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return null;

	}
	

	public List<Computer> findPaging(int currentPage, int limit, NbTotal nbTotal) {
		logger.trace("ENTER GET ALL COMPUTERS");
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(Computer.class);
			criteria.setFirstResult(currentPage * limit);
			criteria.setMaxResults(limit);
			List<Computer> firstPage = criteria.list();
			Criteria criteriaCount = session.createCriteria(Computer.class);
			criteriaCount.setProjection(Projections.rowCount());
			nbTotal.nomberOfComputer = ((Long) criteriaCount.uniqueResult()).intValue();
			return firstPage;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return new ArrayList<>();
	}


	public List<Computer> getComputerByUserId(int user_id) {
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(Computer.class, "computer").createCriteria("computer.user", "user")
					.add(Restrictions.eq("user.id", user_id));
			List<Computer> computers = cr.list();
			return computers;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		} finally {
			session.close();
		}
		return null;
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
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = DELETE_FROM_ID;
			session.createQuery(hql).setString("id", id + "").executeUpdate();
			tx.commit();
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
			return false;
		} finally {
			session.close();
		}
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
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			session.persist(computer);
			tx.commit();
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
			return false;
		} finally {
			session.close();
		}
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
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			session.merge(computer);
			tx.commit();
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	public boolean deleteComputerFromCompany(int id) {
		logger.trace("ENTER REMOVE COMPUTER");
		Session session = sessionFactory.openSession();
		try {
			Transaction tx = session.beginTransaction();
			String hql = DELETE_FROM_COMPANY_ID;
			session.createQuery(hql).setString("id", id + "").executeUpdate();
			tx.commit();
			return true;
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
			return false;
		} finally {
			session.close();
		}
	}

}
