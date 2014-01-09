package org.sportTracker.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jboss.logging.Logger;

public class PersistenceManager {

	private Logger log = Logger.getLogger(this.getClass());
	private static final PersistenceManager singleton = new PersistenceManager();
	protected EntityManagerFactory emf;

	public static PersistenceManager getInstance() {
		return singleton;
	}

	private PersistenceManager() {
	}

	public EntityManagerFactory getEntityManagerFactory() {
		if (emf == null)
			createEntityManagerFactory();
		return emf;
	}

	public void closeEntityManagerFactory() {
		if (emf != null) {
			emf.close();
			emf = null;
			log.debug("n*** Persistence finished at " + new java.util.Date());
		}
	}

	protected void createEntityManagerFactory() {
		this.emf = Persistence.createEntityManagerFactory("sportTracker");
		log.debug("n*** Persistence started at " + new java.util.Date());
	}
}