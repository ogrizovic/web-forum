package webprog.forum.repo;

import java.util.Map;

public interface CrudRepo<T> {

	public void create(T obj);
	public T readOne(T obj);
	public T readOneById(String id);
	public Map<String, T> readAll();
	public void update(T obj, String id);
	public void delete(String id);
}
