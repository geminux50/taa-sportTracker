package org.sportTracker.ws;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.Response;

import org.sportTracker.model.User;

/**
 * 
 * @author Legendre & Favereau
 * @version 1.0.0
 * 
 *          Interface de definition des services web associe a la gestion des
 *          utilisateurs de l'Application 'SportTrackAppli'
 * 
 *          Le 12/10/2013
 * 
 */

public interface UserServices {

	/**
	 * @see User.class
	 * @category service web (fonction) Demande la creation d'un utilisateur
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public Response createUser(User user);

	/**
	 * @see User.class
	 * @category service web (fonction) Supprime un ami du compte de
	 *           l'utilisateur
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public String deleteFriend();

	/**
	 * @see User.class
	 * @category service web (fonction) Supprime un utilisateur
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public boolean deleteUser(String tokenKey, String pseudo);

	/**
	 * @see User.class
	 * @category service web (fonction) Recherche et retourne un ami
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public String getFriendByName();


	/**
	 * @see User.class
	 * @category service web (fonction) Recherche et retourne un utilisateur
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public String getUserByName();

	/**
	 * @see User.class
	 * @category service web (fonction) Mise a jour de l'utilisateur dans la
	 *           base de donnee
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public String updateUser();

	public Response login(User user);

	public Response addFriend(String pseudo, String friendPseudo);
	
	public Response delFriend(String pseudo, String friendPseudo);


	public Collection<User> getFriends(String tokenKey, String pseudo);

	public Collection<User> getSubscribers(String tokenKey, String pseudo);

	public Response logout(User user);

}