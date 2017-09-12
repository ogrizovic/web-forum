package webprog.forum.controller;

import java.security.Principal;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import webprog.forum.auth.Role;
import webprog.forum.auth.Secured;

@Path("sec")
public class SecuredCtrl {

	public SecuredCtrl() {
	}
	
	
	// Provera koji user gadja endpoint
	@GET
	@Secured({Role.USER})
	@Produces(MediaType.APPLICATION_JSON)
	public Response myMethod(@Context SecurityContext securityContext) {
		
		Principal principal = securityContext.getUserPrincipal();
		String username = principal.getName();
		
		
		return Response.ok("User: " + username + " called Secured myMethod.").build();
	}
}
