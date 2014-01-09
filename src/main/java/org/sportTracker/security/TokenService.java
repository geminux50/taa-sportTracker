package org.sportTracker.security;

import java.util.Calendar;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.jboss.logging.Logger;
import org.sportTracker.dao.impl.UserDao;
import org.sportTracker.persistence.PersistenceManager;

public class TokenService {

	private static Logger log = Logger.getLogger(TokenAuth.class);
	private static EntityManagerFactory emf = PersistenceManager.getInstance()
			.getEntityManagerFactory();

	public static String generateTokenKey() {
		String token = UUID.randomUUID().toString().toUpperCase() + "_"
				+ Calendar.getInstance().getTimeInMillis();
		return token;
	}

	public static boolean isValid(String tokenKey) {
		boolean b = false;
		EntityManager em = emf.createEntityManager();
		UserDao userDao = new UserDao(em);

		try {
			if (userDao.tokenKeyIsValid(tokenKey)) {
				log.info("tokenKey " + tokenKey + " is valid");
				b = true;
			} else {
				log.info("tokenKey " + tokenKey + " is NOT valid");
			}
			return b;
		} finally {
			em.close();
		}

	}

}
