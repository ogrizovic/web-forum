package webprog.forum.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import webprog.forum.model.Tema;
import webprog.forum.service.TemaService;

@Path("search")
public class SearchCtrl {

	private TemaService temaService;
	
	public SearchCtrl() {
		this.temaService = new TemaService();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("s_naslov") String naslov,
			@QueryParam("s_sadrzaj") String sadrzaj,
			@QueryParam("s_autor") String autor,
			@QueryParam("s_podforum") String podforum,
			@QueryParam("s_tip") String tip) {
		
		if (podforum == null) {
			podforum = "";
		}
		
		Collection<Tema> teme = temaService.getAll().values();
		ArrayList<Tema> matchedList = new ArrayList<>();
		
		for (Tema entry : teme) {
			if (entry.getNaslov().toLowerCase().contains(naslov.toLowerCase()) && 
			entry.getSadrzaj().toLowerCase().contains(sadrzaj.toLowerCase()) && 
			entry.getAutor().toLowerCase().contains(autor.toLowerCase()) && 
			entry.getParentPodforum().getNaziv().toLowerCase().contains(podforum.toLowerCase()) && 
			entry.getTipTeme().toString().toLowerCase().contains(tip.toLowerCase()))
			{
				matchedList.add(entry);
			}
		}
		
		GenericEntity<ArrayList<Tema>> list = new GenericEntity<ArrayList<Tema>>(matchedList) {};
		return Response.ok(list).build();
	}
	
	
	
	
	
	
	
	
}
