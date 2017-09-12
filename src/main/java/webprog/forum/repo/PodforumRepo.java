package webprog.forum.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import webprog.forum.model.Podforum;

public class PodforumRepo extends DefaultCrudRepo<Podforum> {

	public PodforumRepo() {
		super();
		setMapType(new TypeToken<Map<String, Podforum>>() {}.getType());
		setPath("src/main/resources/repository/podforumi.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Podforum> readAll() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(getPath())));
			setObjectsMap((Map<String, Podforum>) getParser().fromJson(reader, getMapType()));
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
