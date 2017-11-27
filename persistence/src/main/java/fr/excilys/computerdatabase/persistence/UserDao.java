package fr.excilys.computerdatabase.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.excilys.computerdatabase.model.User;

@Component
public class UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Autowired
	private SessionFactory sessionFactory;

	public User findUserByUsername(String username) {
		Session session = sessionFactory.openSession();
		try {
			Criteria cr = session.createCriteria(User.class);
			cr.add(Restrictions.eq("username", username));
			return (User) cr.list().get(0);
		} catch (HibernateException | IndexOutOfBoundsException e) {
			logger.error("Error while getting a user");

		}
		return null;
	}

	public List<String> findRolesForUser(String username) {

		Session session = sessionFactory.openSession();
		try {
			session.createQuery("select * from user_roles where username =" + username);
		} catch (HibernateException e) {
			logger.error("Error while getting a user");

		}
		return new ArrayList<>();

	}

}
