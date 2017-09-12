package webprog.forum.service;

import java.util.Map;

import webprog.forum.model.IDInterface;
import webprog.forum.repo.DefaultCrudRepo;

public abstract class DefaultCrudService<T extends IDInterface> implements CrudService<T> {

	private DefaultCrudRepo<T> repo;
	
	public DefaultCrudService(DefaultCrudRepo<T> repo) {
		this.repo = repo;
	}
	
	@Override
	public T add(T obj) {
		repo.create(obj);
		return repo.readOne(obj);
	}

	@Override
	public T getOne(T obj) {
		return repo.readOne(obj);
	}

	@Override
	public T getOneById(String id) {
		return repo.readOneById(id);
	}

	@Override
	public Map<String, T> getAll() {
		return repo.readAll();
	}

	@Override
	public T edit(T obj) {
		repo.update(obj);
		return repo.readOne(obj);
	}

	@Override
	public void remove(String id) {
		repo.delete(id);
	}

	public DefaultCrudRepo<T> getRepo() {
		return repo;
	}

	public void setRepo(DefaultCrudRepo<T> repo) {
		this.repo = repo;
	}

	
}
