package webprog.forum.service;

import java.util.Map;

import webprog.forum.model.User;
import webprog.forum.repo.UserRepo;

public class UserService implements CrudService<User>{

	private UserRepo repo;
	
	
	public UserService()  {
		this.repo = new UserRepo();
	}
	
	@Override
	public User add(User obj) {
		repo.create(obj);
		return repo.readOne(obj);
	}

	@Override
	public User getOne(User obj) {
		return repo.readOne(obj);
	}

	@Override
	public User getOneById(String id) {
		return repo.readOneById(id);
	}

	@Override
	public Map<String, User> getAll() {
		return repo.readAll();
	}

	@Override
	public User edit(User obj, String id) {
		repo.update(obj, id);
		return repo.readOne(obj);
	}

	@Override
	public void remove(String id) {
		repo.delete(id);
	}
	
	
	

}
