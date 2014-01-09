package org.sportTracker.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * Jersey Token-based Authentication filter
 * 
 * @author Laurent Legendre
 */
public class AuthFilter implements ContainerRequestFilter {
	/**
	 * Apply the filter : check input request, validate or not with user
	 * tokenKey
	 * 
	 * @param containerRequest
	 *            The request from Tomcat server
	 */

	private Logger log = Logger.getLogger(this.getClass());


	@Override
	public ContainerRequest filter(ContainerRequest containerRequest)
			throws WebApplicationException {

		// Retrieve method (example : GET, POST, PUT, DELETE)
		String method = containerRequest.getMethod();

		// Retrieve path (example : /user/pseudo/getFriends)
		String path = containerRequest.getPath(true);

		// WhiteList : Retrieve WADL
		if (method.equals("GET")
				&& (path.equals("application.wadl") || path
						.equals("application.wadl/xsd0.xsd"))) {
			return containerRequest;
		}

		// WhiteList : Register (Creating new accounts)
		if (method.equals("POST") && (path.equals("user/create"))) {
			return containerRequest;
		}

		// WhiteList : Login (Creating new accounts)
		if (method.equals("POST") && (path.equals("user/login"))) {
			return containerRequest;
		}

		// Retrieve the token key passed in HTTP headers parameters
		String tokenKey = containerRequest
				.getHeaderValue("x-sportTracker-tokenKey");

		// If the user does not send x-sportTracker-tokenKey header
		if (tokenKey == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		// Check token validity
		if (TokenAuth.isValid(tokenKey)) {
			log.info("access granted on " + path + " to user with tokenKey="
					+ tokenKey);
		} else {
			log.info("access REJECTED on " + path + " to user with tokenKey="
					+ tokenKey);
			throw new WebApplicationException(Status.UNAUTHORIZED);

		}

		return containerRequest;

	}
}
