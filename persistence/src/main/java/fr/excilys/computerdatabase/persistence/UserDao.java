package fr.excilys.computerdatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.model.Description;
import fr.excilys.computerdatabase.model.Role;
import fr.excilys.computerdatabase.model.User;

@Component
public class UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	private final String DESCRIPTION = "SELECT d.id, d.firstname FROM description d LEFT JOIN  user u ON d.user_id = u.id LEFT JOIN  company c ON d.company_id = c.id WHERE u.username= :username";

	@Autowired
	private SessionFactory sessionFactory;

	public User findUserByUsername(String username) {
		try (Session session = sessionFactory.openSession();) {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			return (User) cr.list().get(0);
		} catch (HibernateException | IndexOutOfBoundsException e) {
			logger.error("Error while getting a user");
		}
		return null;
	}
	

	public User findUserById(int user_id) {
		try (Session session = sessionFactory.openSession();) {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("id", user_id));
			return (User) cr.uniqueResult();
		} catch (HibernateException | IndexOutOfBoundsException e) {
			logger.error("Error while getting a user");
		}
		return null;
	}


	public List<Role> findRolesForUser(String username) {
		try (Session session = sessionFactory.openSession();) {
			Criteria cr = session.createCriteria(Role.class);
			cr.add(Restrictions.eq("username", username));
			return cr.list();
		} catch (HibernateException e) {
			logger.error("Error while getting a role");
		}
		return new ArrayList<>();
	}

	public void createUser(User userToInsert) {
		try (Session session = sessionFactory.openSession();) {
			Transaction tx = session.beginTransaction();
			session.persist(userToInsert);
			Role role = new Role(userToInsert.getUsername(), "ROLE_USER");
			session.persist(role);
			Description description = new Description();
			description.setUser(userToInsert);
			session.persist(description);
			tx.commit();
		} catch (HibernateException he) {
			logger.error("Error while getting a computer");
		}
	}

	public boolean usernameNotExist(String username) {

		try (Session session = sessionFactory.openSession();) {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			return cr.list().isEmpty();
		} catch (HibernateException e) {
			logger.error("Error while getting a role");

		}
		return false;
	}

	public void modifyDescription(Description description) {
		try (Session session = sessionFactory.openSession();) {
			Transaction tx = session.beginTransaction();
			session.merge(description);
			tx.commit();
		} catch (HibernateException e) {
			logger.error("Error while getting a role");

		}
	}

	public Description findDescription(String username) {
		try (Session session = sessionFactory.openSession();) {
			Description desc =  (Description) session.createCriteria(Description.class, "desc")
					.createCriteria("desc.user", "user").add(Restrictions.eq("user.username", username))
					.uniqueResult();
			return desc;
		} catch (HibernateException e) {
			logger.error("Error while getting a role");
			return new Description();
		}
	}
	
	public List<Description> findAllDescriptions() {
		List<Description> list = null;
		try (Session session = sessionFactory.openSession();) {
			list = session.createCriteria(Description.class).list();
		} catch (HibernateException e) {
			logger.error("Error while getting all descriptions");
		}
		return list;
	}

}
