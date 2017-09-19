package webprog.forum.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import webprog.forum.model.Tema;

public class TemaRepo extends DefaultCrudRepo<Tema> {
	
	public TemaRepo() {
		super();
		setMapType(new TypeToken<Map<String, Tema>>() {}.getType());
		setPath(getPath() + "/teme.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Tema> readAll() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(getPath())));
			setObjectsMap((Map<String, Tema>) getParser().fromJson(reader, getMapType()));
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
	
	// <naslov, Tema>
	public Map<String, Tema> getAllForPodforum(String podforumId){
		readAll();
		Map<String, Tema> temePodforuma = new HashMap<>();
		for (Map.Entry<String, Tema> entry : getObjectsMap().entrySet()) {
			if (entry.getValue().getParentPodforum().getId().equals(podforumId)) {
				temePodforuma.put(entry.getValue().getNaslov(), entry.getValue());
			}
		}
		return temePodforuma;
	}
	
	public void deleteAllForPodforum(String podforumId) {
		readAll();
		Map<String, Tema> preostaleTemePodforuma = new HashMap<>();
		for (Map.Entry<String, Tema> entry : getObjectsMap().entrySet()) {
			if (!entry.getValue().getParentPodforum().getId().equals(podforumId)) {
				preostaleTemePodforuma.put(entry.getValue().getNaslov(), entry.getValue());
			}
		}
		setObjectsMap(preostaleTemePodforuma);
		saveMap(getPath());
	}
	
	
	
	
}
