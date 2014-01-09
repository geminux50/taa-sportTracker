package org.sportTracker.dao;

import javax.persistence.EntityManager;

/**
 * 
 * @author Legendre & Favereau
 * 
 *         Share the same EntityManager between all subClasses This will avoid
 *         LazyLoading Exception when retrieving datas
 */
public abstract class Dao {

	protected EntityManager em;

	public Dao(EntityManager em) {
		this.em = em;
	}
}
