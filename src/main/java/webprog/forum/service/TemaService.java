package webprog.forum.service;

import java.util.Map;

import webprog.forum.model.Tema;
import webprog.forum.repo.TemaRepo;

public class TemaService extends DefaultCrudService<Tema> {

	
	public TemaService() {
		super(new TemaRepo());
	}
	
	public Map<String, Tema> getTemePodforuma(String podforumId) {
		return ((TemaRepo) getRepo()).getAllForPodforum(podforumId);
	}
	
	public void removeTemePodforuma(String podforumId) {
		((TemaRepo) getRepo()).deleteAllForPodforum(podforumId);
	}
}
