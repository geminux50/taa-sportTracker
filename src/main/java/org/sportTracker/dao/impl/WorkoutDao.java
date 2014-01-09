package org.sportTracker.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.jboss.logging.Logger;
import org.sportTracker.dao.Dao;
import org.sportTracker.model.User;
import org.sportTracker.model.Workout;

public class WorkoutDao extends Dao {

	private Logger log = Logger.getLogger(this.getClass());

	public WorkoutDao(EntityManager em) {
		super(em);
	}

	public User getUserByPseudo(String pseudo) {

		User user = null;

		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.pseudo = :pseudo")
					.setParameter("pseudo", pseudo);
			user = (User) query.getSingleResult();
			log.debug("User " + user.getPseudo() + " found");
		} catch (NoResultException nrE) {
			log.info("User with '" + pseudo + "' as pseudo does not exists");
			log.debug(nrE.getMessage());
		} catch (NonUniqueResultException nurE) {
			log.info("User with '" + pseudo + "' as pseudo is not unique");
			log.debug(nurE.getMessage());
		}

		return user;
	}

	public User getUserByMail(String mail) {

		User user = null;

		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.mail = :mail").setParameter(
					"mail", mail);
			user = (User) query.getSingleResult();
			log.debug("User " + user.getMail() + " has been found in DB");
		} catch (NoResultException nrE) {
			log.info("User with '" + mail + "' as mail does NOT exists");
			log.debug(nrE.getMessage());
		} catch (NonUniqueResultException nurE) {
			log.info("User with '" + mail + "' as mail is NOT unique");
			log.debug(nurE.getMessage());
		}

		return user;
	}

	public boolean mailIsAvailable(String mail) {

		boolean status = false;

		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.mail = :mail").setParameter(
					"mail", mail);
			if (query.getResultList().isEmpty()) {
				status = true;
				log.debug("Email " + mail + " is available");
			} else {
				log.debug("Email " + mail + " is NOT available");
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public boolean pseudoIsAvailable(String pseudo) {

		boolean status = false;

		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.pseudo = :pseudo")
					.setParameter("pseudo", pseudo);
			if (query.getResultList().isEmpty()) {
				status = true;
				log.debug("Pseudo " + pseudo + " is available");
			} else {
				log.debug("Pseudo " + pseudo + " is NOT available");
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public Collection<User> getUserFriends(User user) {
		Collection<User> friends = new ArrayList<User>();
		try {
			Query query = em.createQuery(
					"SELECT u.friends FROM User u WHERE u.pseudo = :pseudo")
					.setParameter("pseudo", user.getPseudo());

			friends = new ArrayList<User>(query.getResultList());
		} catch (Exception e) {
			log.debug(e);
		}

		return friends;
	}

	public Collection<User> getUserSubscribers(User user) {
		Collection<User> subscribers = new ArrayList<User>();
		try {
			Query query = em
					.createQuery(
							"SELECT u FROM User u JOIN FETCH u.friends f WHERE f.pseudo = :pseudo")
					.setParameter("pseudo", user.getPseudo());

			subscribers = new ArrayList<User>(query.getResultList());
		} catch (Exception e) {
			log.debug(e);
		}

		return subscribers;
	}

	public Set<Workout> getUserWorkouts(Workout Workout) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean tokenKeyIsValid(String tokenKey) {
		boolean status = false;
		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.tokenKey = :tokenKey")
					.setParameter("tokenKey", tokenKey);
			if (query.getResultList().isEmpty()) {
				status = false;
				log.debug("TokenKey " + tokenKey + " does NOT exists");
			} else if (query.getResultList().size() > 1) {
				status = false;
				log.debug("TokenKey " + tokenKey + " is NOT unique");
			} else {
				status = true;
				log.debug("TokenKey " + tokenKey + " found");
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public boolean tokenKeyIsValidAndMatchUser(String tokenKey, User user) {
		boolean status = false;
		try {
			Query query = em.createQuery(
					"SELECT u FROM User u WHERE u.tokenKey = :tokenKey")
					.setParameter("tokenKey", tokenKey);
			if (query.getResultList().isEmpty()) {
				status = false;
				log.debug("TokenKey " + tokenKey + " does NOT exists");
			} else if (query.getResultList().size() > 1) {
				status = false;
				log.debug("TokenKey " + tokenKey + " is NOT unique");
			} else {
				User userDb = (User) query.getSingleResult();
				if (userDb.equals(user)) {
					log.debug("TokenKey " + tokenKey + " found and user "
							+ user.getPseudo() + " is the owner.");
					status = true;
				} else {
					log.debug("TokenKey " + tokenKey + " found BUT user "
							+ user.getPseudo() + " is NOT the owner.");
					status = false;
				}
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public boolean createUser(User user) {
		boolean status = false;
		try {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.persist(user);
				t.commit();
				status = true;
			} finally {
				if (t.isActive()) {
					t.rollback();
					status = false;
				}
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public boolean updateUser(User user) {
		boolean status = false;
		try {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(user);
				t.commit();
				status = true;
			} finally {
				if (t.isActive()) {
					t.rollback();
					status = false;
				}
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

	public boolean deleteUser(User user) {
		boolean status = false;
		try {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(user);
				t.commit();
				status = true;
			} finally {
				if (t.isActive()) {
					t.rollback();
					status = false;
				}
			}
		} catch (Exception e) {
			log.debug(e);
		}

		return status;
	}

}
