package webprog.forum.service;

import java.util.Map;

import webprog.forum.model.Komentar;
import webprog.forum.repo.KomentarRepo;

public class KomentarService extends DefaultCrudService<Komentar> {

	public KomentarService() {
		super(new KomentarRepo());
	}
	
	public Map<String, Komentar> getKomentariTeme(String temaId) {
		return ((KomentarRepo) getRepo()).getAllForTema(temaId);
	}
	
	public void removeKomentariTeme(String temaId) {
		((KomentarRepo) getRepo()).removeAllForTema(temaId);
	}
	
	public void removePodkomentari(String komentarId) {
		((KomentarRepo) getRepo()).removeAllForKomentar(komentarId);
	}
}
