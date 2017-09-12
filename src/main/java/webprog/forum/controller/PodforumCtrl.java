package webprog.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import webprog.forum.auth.Role;
import webprog.forum.auth.Secured;
import webprog.forum.model.Podforum;
import webprog.forum.model.User;
import webprog.forum.service.PodforumService;
import webprog.forum.service.UserService;

@Path("podforumi")
public class PodforumCtrl {

	private PodforumService service;
	private UserService userService;
	
	public PodforumCtrl() {
		this.service = new PodforumService();
		this.userService = new UserService();
	}
	
	@GET
	@Produces
	public Response getAll() {
		Collection<Podforum> allPodforumi = service.getAll().values();
		GenericEntity<Collection<Podforum>> list = new GenericEntity<Collection<Podforum>>(allPodforumi) {};
		return Response.ok(list).build();
	}
	
	@Secured({Role.ADMIN, Role.MODERATOR})
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(Podforum podforum, @Context SecurityContext securityContext) {
		// ADMIN i MOD, postaju odg MOD.
		if (service.getOneById(podforum.getNaziv()) != null) {
			return Response.status(Status.CONFLICT).build();
		}
		
		
		podforum.setId(service.getRepo().getNewId());
		String username = securityContext.getUserPrincipal().getName();
		podforum.setOdgovorniModerator(userService.getOneById(username));
		service.add(podforum);
		
		URI resourceUri = null;
		try {
			resourceUri = new URI("podforumi/" + podforum.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).entity(service.getOneById(podforum.getId())).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam(value = "id") String id) {
		Podforum tmp = service.getOneById(id);
		if (tmp != null) {
			return Response.status(Status.OK).entity(tmp).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Secured({Role.ADMIN, Role.MODERATOR})
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(Podforum podforum) {
		Podforum tmp = service.getOneById(podforum.getId());
		if (tmp == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		tmp = service.edit(podforum);
		return Response.ok(tmp).build();
	}
	
	@Secured({Role.ADMIN, Role.MODERATOR})
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@Context SecurityContext securityContext, @PathParam(value="id") String id) {
		// ADMIN i odg. MOD
		String username = securityContext.getUserPrincipal().getName();
		User user = userService.getOneById(username);
		boolean isAdmin = (user.getUloga() == Role.ADMIN);
		
		Podforum tmp = service.getOneById(id);
		if (tmp != null) {
			if (isAdmin || username.equals(tmp.getOdgovorniModerator().getUsername())) {
				service.remove(id);
				return Response.ok().build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.status(Status.NOT_FOUND).build();
		
	}
	
}
