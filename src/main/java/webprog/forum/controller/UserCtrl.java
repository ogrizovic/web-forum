package webprog.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import webprog.forum.auth.AuthenticationService;
import webprog.forum.auth.Role;
import webprog.forum.auth.Secured;
import webprog.forum.model.Komentar;
import webprog.forum.model.Podforum;
import webprog.forum.model.Tema;
import webprog.forum.model.User;
import webprog.forum.service.PodforumService;
import webprog.forum.service.TemaService;
import webprog.forum.service.UserService;

@Path("users")
public class UserCtrl {

	private UserService service;
	private AuthenticationService authService;
	private TemaService temaService;
	private PodforumService podforumService;
	
	public UserCtrl() {
		this.service = new UserService();
		this.authService = new AuthenticationService();
		this.temaService = new TemaService();
	}
	
	@Secured
	@GET
	@Path("{username}/prati")
	public Response pratiPodforum(@PathParam(value="username") String username,
			@QueryParam("podforumId") String podforumId) {
		
		User user = service.getOneById(username);
		user.getPraceniPodforumi().add(podforumId);
		service.edit(user, username);
		
		return Response.ok(user).build();
	}
	
	@GET
	@Path("{userId}/praceneTeme")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response praceneTeme(@PathParam(value="userId") String userId) {
		User user = service.getOneById(userId);
		Map<String, Tema> praceneTeme = new HashMap<>();
		Tema tema;
		for (String podforumId : user.getPraceniPodforumi()) {
			praceneTeme.putAll(temaService.getTemePodforuma(podforumId));
		}
		return Response.ok(praceneTeme).build();
	}
	
	@GET
	@Path("{userId}/logout")
	@Produces(MediaType.TEXT_PLAIN)
	public Response logout(@Context HttpHeaders headers) {
		List<String> authHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
		String auth = authHeader.get(0);
		String[] splitted = auth.split(" ");
		String token = splitted[1];
		
		authService.delete(token);
		return Response.ok().build();
	}
	
	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response register(User newUser) {
		if (service.getOneById(newUser.getUsername()) != null) {
			return Response.status(Status.CONFLICT).build();
		}
		
		newUser.setPraceniPodforumi(new ArrayList<String>());
		newUser.setTeme(new ArrayList<Tema>());
		newUser.setKomentari(new ArrayList<Komentar>());
		newUser.setDatumRegistracijeNow();
		newUser.setUloga(Role.USER);
		service.add(newUser);
		
		URI resourceUri = null;
		try {
			resourceUri = new URI("users/" + newUser.getUsername());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
 		return Response.created(resourceUri)
 				.entity(newUser.getUsername()).build();
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
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(@PathParam(value = "username") String username, 
			@QueryParam("mod") String mod) {
		User tmp = service.getOneById(username);
		if (tmp == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		tmp.setUloga(Role.MODERATOR);
		tmp = service.edit(tmp, tmp.getId());
		return Response.ok(tmp).build();
	}
	
}
