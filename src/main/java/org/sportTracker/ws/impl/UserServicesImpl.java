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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User candidateUser) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		JSONObject response = new JSONObject();
		try {
			String ret_msg = "Failed - Unknown error";
			boolean ret_succeed = false;
			if (candidateUser != null) {
				User existingUser = userDao.getUserByPseudo(candidateUser
						.getPseudo());
				if (existingUser != null) {
					if (existingUser.getPassword().equals(
							candidateUser.getPassword())) {
						log.debug("User " + candidateUser.getPassword()
								+ " found.");
						String tokenKey = TokenService.generateTokenKey();
						existingUser.setTokenKey(tokenKey);
						ret_msg = tokenKey;
						ret_succeed = true;
						if (!userDao.updateUser(existingUser)) {
							throw new WebApplicationException(new Throwable(
									"Unable to store tokenKey"),
									Status.INTERNAL_SERVER_ERROR);
						}
					} else {
						log.debug("User authentication for '"
								+ candidateUser.getPseudo()
								+ "' FAILED (Wrong password).");
						ret_msg = "Mauvais mot de passe";
					}
				} else {
					log.debug("User authentication for '"
							+ candidateUser.getPseudo()
							+ "' FAILED (User does NOT exists).");
					ret_msg = "utilisateur inconnu";
				}

			}
			if (ret_succeed) {
				response.put("token", ret_msg);
			} else {
				response.put("error", ret_msg);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
		return Response.status(201).entity(response).build();

	}
	
	/**
	 * Permet à un utilisateur de se déconnecter (destruction du token)
	 */
	@Override
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(User candidateUser) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		JSONObject response = new JSONObject();
		try {
			String ret_msg = "Failed - Unknown error";
			boolean ret_succeed = false;
			if (candidateUser != null) {
				User existingUser = userDao.getUserByPseudo(candidateUser
						.getPseudo());
				if (existingUser != null) {
					if (existingUser.getPassword().equals(
							candidateUser.getPassword())) {
						log.debug("User " + candidateUser.getPassword()
								+ " found.");
						String tokenKey = TokenService.generateTokenKey();
						existingUser.setTokenKey(tokenKey);
						ret_msg = tokenKey;
						ret_succeed = true;
						if (!userDao.updateUser(existingUser)) {
							throw new WebApplicationException(new Throwable(
									"Unable to store tokenKey"),
									Status.INTERNAL_SERVER_ERROR);
						}
					} else {
						log.debug("User authentication for '"
								+ candidateUser.getPseudo()
								+ "' FAILED (Wrong password).");
						ret_msg = "Mauvais mot de passe";
					}
				} else {
					log.debug("User authentication for '"
							+ candidateUser.getPseudo()
							+ "' FAILED (User does NOT exists).");
					ret_msg = "utilisateur inconnu";
				}

			}
			if (ret_succeed) {
				response.put("token", ret_msg);
			} else {
				response.put("error", ret_msg);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
		return Response.status(201).entity(response).build();

	}

	/**
	 * Crée un nouvel utilisateur
	 */
	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		JSONObject response = new JSONObject();
		try {
			boolean ret_succeed = false;
			String ret_msg = "Failed - Unknown error";
			if (user != null) {

				// ensure expected values are set and NOT null
				if (user.getPseudo() != null && user.getMail() != null
						&& user.getPassword() != null) {
					if (!user.getPseudo().trim().isEmpty()
							&& !user.getMail().trim().isEmpty()
							&& !user.getPassword().trim().isEmpty()) {

						// check availibility of unique attributes
						if (userDao.pseudoIsAvailable(user.getPseudo())) {
							if (userDao.mailIsAvailable(user.getMail())) {
								if (userDao.createUser(user)) {
									ret_msg = "Utilisateur créé avec succès";
									ret_succeed = true;
								} else {
									ret_msg = "Erreur de création de l'utilisateur";
								}
							} else {
								ret_msg = "Cette adresse email est déjà utilisée";
							}
						} else {
							ret_msg = "Ce pseudo est déjà utilisé";
						}

					} else {
						ret_msg = "Veuillez renseigner un pseudo, un email et un mot de passe";
					}
				} else {
					ret_msg = "Veuillez renseigner un pseudo, un email et un mot de passe";
				}
			}

			if (ret_succeed) {
				response.put("success", ret_msg);
			} else {
				response.put("error", ret_msg);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
		return Response.status(201).entity(response).build();

	}

	@Override
	@POST
	@Path("/{pseudo}/addFriend/{friendPseudo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFriend(@PathParam("pseudo") String pseudo,
			@PathParam("friendPseudo") String friendPseudo) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		JSONObject response = new JSONObject();
		try {
			String ret_msg = "Failed - Unknown error";
			boolean ret_succeed = false;
			userDao = new UserDao(em);
			User owner = userDao.getUserByPseudo(pseudo);
			User newFriend = userDao.getUserByPseudo(friendPseudo);
			
			if (newFriend != null) {
				if (!newFriend.equals(owner)) {
					//newFriend n'est pas null et  je n'essie pas de m'ajouter moi même
					Collection<User> friends = userDao.getUserFriends(owner);
					if (friends.contains(newFriend)) {
						log.info(pseudo + " already have " + friendPseudo
								+ " as a friend");
						ret_succeed = false;
						ret_msg = friendPseudo	+ " est  déjà votre ami";
						
					} else {
						owner.addFriend(newFriend);
						log.info(pseudo + " adding " + friendPseudo + " as a friend");
						ret_succeed = userDao.updateUser(owner);
						if	(ret_succeed) {
							ret_msg = friendPseudo	+ " est maintenant votre ami";
						} else {
							ret_msg = "Impossible d'ajouter " + friendPseudo + " comme ami";
							log.info("Unable to add " + friendPseudo
									+ " as a friend");
						}
							
					}
				} else {
					log.info(friendPseudo
							+ " is yourself");
					ret_msg = "Impossible de s'ajouter soit même";
				}
			}else {
				log.info(friendPseudo
						+ " does not exists");
				ret_msg =  friendPseudo + " n'existe pas";
	
			}
					
			
			if (ret_succeed) {
				response.put("message", ret_msg);
			} else {
				response.put("error", ret_msg);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
		return Response.status(201).entity(response).build();


	}
	
	
	@Override
	@POST
	@Path("/{pseudo}/delFriend/{friendPseudo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delFriend(@PathParam("pseudo") String pseudo,
			@PathParam("friendPseudo") String friendPseudo) {

		EntityManager em = emf.createEntityManager();
		log.debug("Starting new per-request EntityManager: " + em.toString());
		UserDao userDao = new UserDao(em);

		JSONObject response = new JSONObject();
		try {
			String ret_msg = "Failed - Unknown error";
			boolean ret_succeed = false;
			userDao = new UserDao(em);
			User owner = userDao.getUserByPseudo(pseudo);
			User oldFriend = userDao.getUserByPseudo(friendPseudo);
			
			if (oldFriend != null) {
				//newFriend n'est pas null
				if (!oldFriend.equals(owner)) {
					//je n'essie pas de m'ajouter moi même
					Collection<User> friends = userDao.getUserFriends(owner);
					if (friends.contains(oldFriend)) {
						owner.delFriend(oldFriend);
						log.info(pseudo + " removed " + friendPseudo
								+ " as a friend");
						ret_succeed = userDao.updateUser(owner);
						if	(ret_succeed) {
							ret_msg = friendPseudo	+ " n'est plus votre ami";
						} else {
							ret_msg = "Impossible de supprimer " + friendPseudo + " des amis";
							log.info("Unable to remove " + friendPseudo
									+ " as a friend");
						}
					} else {
						ret_msg = friendPseudo + " n'est pas un de vos amis";
						log.info( friendPseudo + " is not as a friend");
					}
				} else {
					log.info(friendPseudo
							+ " is yourself");
					ret_msg = "Impossible de se supprimer soit même";
				}
			}else {
				log.info(friendPseudo
						+ " does not exists");
				ret_msg =  friendPseudo + " n'existe pas";
			}
					
			
			if (ret_succeed) {
				response.put("message", ret_msg);
			} else {
				response.put("error", ret_msg);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			log.debug("Closing per-request EntityManager: " + em.toString());
			em.close();
		}
		return Response.status(201).entity(response).build();


	}

	@Override
	@GET
	@Path("/{pseudo}/getFriends")
	@Consumes(MediaType.APPLICATION_JSON)
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