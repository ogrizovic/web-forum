package webprog.forum.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import webprog.forum.auth.Role;
import webprog.forum.auth.Secured;
import webprog.forum.model.Komentar;
import webprog.forum.model.Tema;
import webprog.forum.model.User;
import webprog.forum.service.PodforumService;
import webprog.forum.service.TemaService;
import webprog.forum.service.UserService;

@Path("podforumi/{podforumId}/teme")
public class TemaCtrl {

	private TemaService service;
	private PodforumService podforumService;
	private UserService userService;
	
	public TemaCtrl() {
		this.service = new TemaService();
		this.podforumService = new PodforumService();
		this.userService = new UserService();
	}
	
	// Vraca sve teme podforuma
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@PathParam(value="podforumId") String podforumId) {
		Collection<Tema> temePodforuma = service.getTemePodforuma(podforumId).values(); 
		GenericEntity<Collection<Tema>> list = new GenericEntity<Collection<Tema>>(temePodforuma) {};
		if (temePodforuma == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(list).build();
	}
	
	@Secured
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@Context SecurityContext securityContext, 
			@PathParam(value="podforumId") String podforumId, Tema tema) {
		// Prijavljeni korisnici
		
		// U ovoj mapi key = naslov teme
		Map<String, Tema> temePodforuma = service.getTemePodforuma(podforumId);
		if (temePodforuma.containsKey(tema.getNaslov())) {
			return Response.status(Status.CONFLICT).build();
		}
		
		String username = securityContext.getUserPrincipal().getName();
		
		tema.setId(service.getRepo().getNewId());
		tema.setParentPodforum(podforumService.getOneById(podforumId));
		tema.setAutor(userService.getOneById(username));
		tema.setDatumKreiranjaNow();
		tema.setLikes(0);
		tema.setDislikes(0);
		tema.setKomentari(new ArrayList<Komentar>());
		
		service.add(tema);
		return Response.ok(service.getOneById(tema.getId())).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@PathParam(value = "id") String id) {
		Tema tmp = service.getOneById(id);
		if (tmp != null) {
			return Response.status(Status.OK).entity(tmp).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	@Secured({Role.USER, Role.ADMIN, Role.MODERATOR})
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response edit(@Context SecurityContext securityContext, Tema tema) {
		//  autori, odg MOD i ADMIN
		
		String username = securityContext.getUserPrincipal().getName();
		User user = userService.getOneById(username);
		boolean isAdmin = (user.getUloga() == Role.ADMIN);
		
		if (username.equals(tema.getAutor().getUsername()) || 
				username.equals(tema.getParentPodforum().getOdgovorniModerator().getUsername()) || 
				isAdmin) {
			
			Tema tmp = service.getOneById(tema.getId());
			if (tmp == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			tmp = service.edit(tema);
			return Response.ok(tmp).build();
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@Secured({Role.USER, Role.ADMIN, Role.MODERATOR})
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@Context SecurityContext securityContext, 
			@PathParam(value="id") String id) {
		// ADMIN i odg. MOD
		
		String username = securityContext.getUserPrincipal().getName();
		User user = userService.getOneById(username);
		boolean isAdmin = (user.getUloga() == Role.ADMIN);
		
		Tema tmp = service.getOneById(id);
		if (tmp != null) {
			if (username.equals(tmp.getAutor().getUsername()) || 
					username.equals(tmp.getParentPodforum().getOdgovorniModerator().getUsername()) || 
					isAdmin) {
				service.remove(id);
				return Response.ok().build();
			}
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
}
