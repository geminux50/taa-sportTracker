package org.sportTracker.ws;

/**
 * 
 * @author Legendre & Favereau
 * @version 1.0.0
 * 
 *          Interface de definition des services web associe au publication sur
 *          des reseaux sociaux de l'Application 'SportTracker'
 * 
 *          Le 12/10/2013
 * 
 */

public interface PublishServices {

	/**
	 * 
	 * @category service web (fonction)
	 * 
	 *           Publie une séance sur le compte facebook de l'utilisateur si il
	 *           existe.
	 * 
	 * @return une info texte pour l'instant...TODO
	 */
	public String publishOnFaceBook();

	/**
	 * 
	 * @category service web
	 * 
	 *           Publie une séance sur le compte twitter de l'utilisateur si il
	 *           existe.
	 * 
	 * @return une info texte pour l'instant...TODO
	 */

	public String publishOnTwitter();

}
