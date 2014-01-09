package org.sportTracker.ws.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sportTracker.ws.PublishServices;


/**
*
* @category Classe
*
* Implementation de l'interface PublishServices */

@Path("/publish")
public class PublishServicesImpl implements PublishServices {

	@Override
	@GET
	@Path("/onFaceBook")
	@Produces(MediaType.TEXT_PLAIN)
	public String publishOnFaceBook() {

		return "I am on FaceBook !!!";
	}

	@Override
	@GET
	@Path("/onTwitter")
	@Produces(MediaType.TEXT_PLAIN)
	public String publishOnTwitter() {

		return "Follow me on Twitter !!!";
	}
}