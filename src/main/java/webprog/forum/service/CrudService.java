package webprog.forum.service;

import java.util.Map;

public interface CrudService<T> {

	public T add(T obj);
	public T getOne(T obj);
	public T getOneById(String id);
	public Map<String, T> getAll();
	public T edit(T obj);
	public void remove(String id);
}
