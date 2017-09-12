package webprog.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import webprog.forum.auth.Role;
import webprog.forum.auth.Secured;
import webprog.forum.model.User;
import webprog.forum.service.UserService;

@Path("users")
public class UserCtrl {

	private UserService service;
	
	public UserCtrl() {
		this.service = new UserService();
	}
	
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User newUser) {
		if (service.getOneById(newUser.getUsername()) != null) {
			return Response.status(Status.CONFLICT).build();
		}
		
		newUser.setDatumRegistracijeNow();
		service.add(newUser);
		
		URI resourceUri = null;
		try {
			resourceUri = new URI("users/" + newUser.getUsername());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
 		return Response.created(resourceUri)
 				.entity(service.getOneById(newUser.getUsername())).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		Collection<User> allUsers = service.getAll().values();
		GenericEntity<Collection<User>> list = new GenericEntity<Collection<User>>(allUsers) {};
		return Response.ok().entity(list).build();
	}
	
	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam(value = "username") String username) {
		User tmp = service.getOneById(username);
		if (tmp != null) {
			return Response.status(Status.OK).entity(tmp).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Secured({Role.ADMIN})
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(User user) {
		User tmp = service.getOneById(user.getId());
		if (tmp == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		tmp = service.edit(user);
		return Response.ok(tmp).build();
	}
	
}
