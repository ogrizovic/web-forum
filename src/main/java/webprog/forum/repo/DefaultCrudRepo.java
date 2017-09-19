package webprog.forum.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import webprog.forum.model.IDInterface;

public abstract class DefaultCrudRepo<T extends IDInterface> implements CrudRepo<T>{
	
	private Gson gson;
	private Map<String, T> objectsMap;
	private Type mapType;
	private String path;
	private static String homeDirectory = System.getProperty("user.home");
	
	public DefaultCrudRepo() {
		this.gson = new GsonBuilder().setPrettyPrinting().create(); 
		setPath(getHomeDirectory() + "/milanforum");
	}
	
	public void saveMap(String path) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(path)));
			getParser().toJson(getObjectsMap(), writer);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getNewId() {
		readAll();
		Collection<T> list = getObjectsMap().values();
		Collection<Integer> ids = new ArrayList<>();
		for (T t : list) {
			ids.add(Integer.parseInt(t.getId()));
		}
		int id;
		if (ids.size() > 0) {
			id = Collections.max(ids);
		}
		else {
			id = 0;
		}
//		Random rnd = new SecureRandom();
//		String id = new BigInteger(64, rnd).toString(32);
		return String.valueOf(id + 1);
	}

	@Override
	public void create(T obj) {
		readAll();
		getObjectsMap().put(obj.getId(), obj);
		saveMap(getPath());
	}


	@Override
	public T readOne(T obj) {
		readAll();
		return getObjectsMap().get(obj.getId());
	}


	@Override
	public T readOneById(String id) {
		readAll();
		return getObjectsMap().get(id);
	}


	@Override
	public Map<String, T> readAll() {
		return null;
	}


	@Override
	public void update(T obj, String id) {
		readAll();
		getObjectsMap().put(id, obj);
		saveMap(getPath());
	}


	@Override
	public void delete(String id) {
		readAll();
		getObjectsMap().remove(id);
		saveMap(getPath());
	}
	

	public void setMapType(Type mapType) {
		this.mapType = mapType;
	}
	
	public Type getMapType() {
		return mapType;
	}

	public Gson getParser() {
		return gson;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, T> getObjectsMap() {
		return objectsMap;
	}


	public void setObjectsMap(Map<String, T> objectsMap) {
		this.objectsMap = objectsMap;
	}

	public static String getHomeDirectory() {
		return homeDirectory;
	}

	public static void setHomeDirectory(String homeDirectory) {
		DefaultCrudRepo.homeDirectory = homeDirectory;
	}
}
