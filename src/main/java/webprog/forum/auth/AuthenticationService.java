package webprog.forum.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import webprog.forum.repo.DefaultCrudRepo;

public class AuthenticationService extends DefaultCrudRepo<UserToken>{

	public AuthenticationService() {
		super();
		setMapType(new TypeToken<Map<String, UserToken>>() {}.getType());
		setPath("src/main/resources/repository/tokens.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, UserToken> readAll() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(getPath())));
			setObjectsMap((Map<String, UserToken>) getParser().fromJson(reader, getMapType()));
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
