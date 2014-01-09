package org.sportTracker.persistence;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PersistenceApplicationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	    PersistenceManager.getInstance().closeEntityManagerFactory();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

	}

}
