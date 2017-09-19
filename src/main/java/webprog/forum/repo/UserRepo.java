package webprog.forum.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import webprog.forum.model.User;

public class UserRepo extends DefaultCrudRepo<User>{

	public UserRepo() {
		super();
		setMapType(new TypeToken<Map<String, User>>() {}.getType());
		setPath(getPath() + "/users.json");
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, User> readAll() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(getPath())));
			setObjectsMap((Map<String, User>) getParser().fromJson(reader, getMapType()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return getObjectsMap();
	}
	
}
