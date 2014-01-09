package org.sportTracker.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class oldEMF implements ServletContextListener {

	private static javax.persistence.EntityManagerFactory emf;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		emf.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		emf = Persistence.createEntityManagerFactory("sportTracker");

	}

	public static EntityManager createEntityManager() {
		if (emf == null) {
			throw new IllegalStateException("Context is not initialized yet.");
		}

		return emf.createEntityManager();
	}
}
