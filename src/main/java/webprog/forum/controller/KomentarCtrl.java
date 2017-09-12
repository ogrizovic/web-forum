package webprog.forum.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import webprog.forum.auth.Secured;
import webprog.forum.model.Komentar;
import webprog.forum.model.Tema;
import webprog.forum.service.KomentarService;
import webprog.forum.service.TemaService;
import webprog.forum.service.UserService;

@Path("podforumi/{podforumId}/teme/{temaId}/komentari")
public class KomentarCtrl {

	private KomentarService service;
	private TemaService temaService;
	private UserService userService;
	
	public KomentarCtrl() {
		this.service = new KomentarService();
		this.temaService = new TemaService();
		this.userService = new UserService();
	}
	
	// Vraca sve komentare teme
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@PathParam(value="temaId") String temaId) {
		Collection<Komentar> komentariTeme = service.getKomentariTeme(temaId).values(); 
		GenericEntity<Collection<Komentar>> list = new GenericEntity<Collection<Komentar>>(komentariTeme) {};
		if (komentariTeme == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(list).build();
	}
	
	@Secured
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(@Context SecurityContext securityContext, 
			@PathParam(value="podforumId") String podforumId,
			@PathParam(value="temaId") String temaId, 
			Komentar komentar) {
		// prijavljeni korisnici

		String username = securityContext.getUserPrincipal().getName();
		
		komentar.setId(service.getRepo().getNewId());
		komentar.setParentTema(temaService.getOneById(temaId));
		komentar.setAutor(userService.getOneById(username));
		komentar.setDatumKomentaraNow();
		komentar.setLikes(0);
		komentar.setDislikes(0);
		komentar.setIzmenjen(false);
		komentar.setPodkomentari(new ArrayList<Komentar>());
		
		Tema parentTema = temaService.getOneById(temaId);
		
		parentTema.getKomentari().add(komentar);
		
		service.add(komentar);
		temaService.edit(parentTema);
		
		URI resourceUri = null;
		try {
			resourceUri = new URI("podforumi/" + podforumId +
					"/teme/" + temaId + "/komentari/" + komentar.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).entity(service.getOneById(komentar.getId())).build();
	}
	
	@Secured
	@POST
	@Path("{komentarId}/reply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReply(@Context SecurityContext securityContext, 
			@PathParam(value="podforumId") String podforumId,
			@PathParam(value="temaId") String temaId, 
			@PathParam(value="komentarId") String komentarId,
			Komentar komentar) {
		// prijavljeni korisnici

		String username = securityContext.getUserPrincipal().getName();
		
		komentar.setId(service.getRepo().getNewId());
		komentar.setParentTema(temaService.getOneById(temaId));
		komentar.setAutor(userService.getOneById(username));
		komentar.setDatumKomentaraNow();
		komentar.setLikes(0);
		komentar.setDislikes(0);
		komentar.setIzmenjen(false);
		
		Komentar parentKomentar = service.getOneById(komentarId);
		parentKomentar.getPodkomentari().add(komentar);
		
		komentar.setParentKomentar(parentKomentar);
		
		service.add(komentar);
//		service.edit(parentKomentar);
		
		URI resourceUri = null;
		try {
			resourceUri = new URI("podforumi/" + podforumId +
					"/teme/" + temaId + "/komentari/" + komentarId + 
					"/reply/" + komentar.getId());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).entity(service.getOneById(komentar.getId())).build();
	}
}
