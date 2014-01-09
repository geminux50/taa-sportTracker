package org.sportTracker.ws.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import org.sportTracker.dao.impl.UserDao;
import org.sportTracker.model.GenderEnum;
import org.sportTracker.model.User;
import org.sportTracker.persistence.PersistenceManager;
import org.sportTracker.security.TokenService;
import org.sportTracker.ws.UserServices;

@Path("/user")
public class UserServicesImpl implements UserServices {

	private EntityManagerFactory emf = PersistenceManager.getInstance()
			.getEntityManagerFactory();
	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * Permet à un utilisateur existant de récupérer un token d'authentification
	 * à utiliser pour chaque requête future.
	 */
	@Override
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(@FormParam("pseudo") String pseudo,
			@FormParam("password") String password) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		try {
			String tokenKey;
			User candidateUser = userDao.getUserByPseudo(pseudo);
			if (candidateUser != null) {
				if (candidateUser.getPassword().equals(password)) {
					log.debug("User " + pseudo + " found.");
					tokenKey = TokenService.generateTokenKey();
					candidateUser.setTokenKey(tokenKey);
					if (!userDao.updateUser(candidateUser)) {
						throw new WebApplicationException(new Throwable(
								"Unable to store tokenKey"),
								Status.INTERNAL_SERVER_ERROR);
					}
				} else {
					log.debug("User authentication for '" + pseudo
							+ "' FAILED (Wrong password).");
					throw new WebApplicationException(Status.UNAUTHORIZED);
				}
			} else {
				log.debug("User authentication for '" + pseudo
						+ "' FAILED (User does NOT exists).");
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			return tokenKey;
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}

	}

	/**
	 * Crée un nouvel utilisateur
	 */
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(@FormParam("name") String name,
			@FormParam("surname") String surname,
			@FormParam("password") String password,
			@FormParam("birthdate") Date birthdate,
			@FormParam("weight") float weight,
			@FormParam("gender") boolean gender,
			@FormParam("pseudo") String pseudo,
			@FormParam("fbAcct") String fbAcct,
			@FormParam("twAcct") String twAcct, @FormParam("mail") String mail,
			@FormParam("group") String group, @FormParam("avatar") String avatar) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		try {
			String status = "failed";
			if (pseudo != null && mail != null && password != null) {
				if (!pseudo.trim().isEmpty() && !mail.trim().isEmpty()
						&& !password.trim().isEmpty()) {

					// check availibility of unique attributes
					if (userDao.mailIsAvailable(mail)
							&& userDao.pseudoIsAvailable(pseudo)) {
						GenderEnum genderType;
						if (gender) {
							genderType = GenderEnum.MAN;
						} else {
							genderType = GenderEnum.WOMAN;
						}

						User user = new User(name, surname, password,
								birthdate, weight, genderType, pseudo, fbAcct,
								twAcct, mail, avatar, group);

						if (userDao.createUser(user)) {
							status = "success";
						}
					}
				}
			}
			return status;
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}

	}

	@Override
	@POST
	@Path("/{pseudo}/addFriend/{friendPseudo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addFriend(@PathParam("pseudo") String pseudo,
			@PathParam("friendPseudo") String friendPseudo) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		try {
			boolean status = false;
			userDao = new UserDao(em);
			User owner = userDao.getUserByPseudo(pseudo);
			User newFriend = userDao.getUserByPseudo(friendPseudo);

			Collection<User> friends = userDao.getUserFriends(owner);
			if (friends.contains(owner)) {
				log.info(pseudo + " already have " + friendPseudo
						+ " as a friend");
				status = false;
			} else {
				owner.addFriend(newFriend);
				log.info(pseudo + " adding " + friendPseudo + " as a friend");
				status = userDao.updateUser(owner);
			}
			return status;
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}

	}

	@Override
	@GET
	@Path("/{pseudo}/getFriends")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getFriends(
			@HeaderParam("x-sportTracker-tokenKey") String tokenKey,
			@PathParam("pseudo") String pseudo) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);
		Collection<User> friends = null;

		try {
			User user = userDao.getUserByPseudo(pseudo);
			if (userDao.tokenKeyIsValidAndMatchUser(tokenKey, user)) {
				friends = new ArrayList<User>(userDao.getUserFriends(user));
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			return friends;

		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
	}

	@Override
	@GET
	@Path("/{pseudo}/getSubscribers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getSubscribers(
			@HeaderParam("x-sportTracker-tokenKey") String tokenKey,
			@PathParam("pseudo") String pseudo) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);
		Collection<User> subscribers = null;

		try {
			User user = userDao.getUserByPseudo(pseudo);
			if (userDao.tokenKeyIsValidAndMatchUser(tokenKey, user)) {
				subscribers = new ArrayList<User>(
						userDao.getUserSubscribers(user));
			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			return subscribers;

		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
	}

	@Override
	public String deleteFriend() {
		// TODO Auto-generated method stub

		return null;
	}

	/**
	 * Delete a user. A user can only be deleted by itself
	 */
	@Override
	@DELETE
	@Path("/{pseudo}/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	public boolean deleteUser(
			@HeaderParam("x-sportTracker-tokenKey") String tokenKey,
			@PathParam("pseudo") String pseudo) {
		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);
		boolean status = false;

		try {
			User user = userDao.getUserByPseudo(pseudo);
			if (userDao.tokenKeyIsValidAndMatchUser(tokenKey, user)) {
				boolean noFailInLoop = true;

				for (User subscriber : userDao.getUserSubscribers(user)) {
					if (subscriber.delFriend(user)) {
						log.info(pseudo + " has deleted " + user
								+ " from it's friends");
						if (!userDao.updateUser(subscriber)) {
							noFailInLoop = false;
						}
					}
				}

				if (noFailInLoop) {
					status = userDao.deleteUser(user);
				}

			} else {
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			return status;
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
	}

	@Override
	public String getFriendByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFriends() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserByName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUser() {
		// TODO Auto-generated method stub
		return null;
	}

}