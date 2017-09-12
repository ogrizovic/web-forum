package webprog.forum.service;

import webprog.forum.model.Podforum;
import webprog.forum.repo.PodforumRepo;

public class PodforumService extends DefaultCrudService<Podforum> {

	public PodforumService() {
		super(new PodforumRepo());
	}
	
}
