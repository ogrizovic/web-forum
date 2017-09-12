package webprog.forum.repo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import webprog.forum.model.Komentar;

public class KomentarRepo extends DefaultCrudRepo<Komentar> {

	public KomentarRepo() {
		super();
		setMapType(new TypeToken<Map<String, Komentar>>() {}.getType());
		setPath("src/main/resources/repository/komentari.json");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Komentar> readAll() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(getPath())));
			setObjectsMap((Map<String, Komentar>) getParser().fromJson(reader, getMapType()));
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
	
	public Map<String, Komentar> getAllForTema(String temaId) {
		readAll();
		Map<String, Komentar> komentariTeme = new HashMap<>();
		for (Map.Entry<String, Komentar> entry : getObjectsMap().entrySet()) {
			if (entry.getValue().getParentTema().getId().equals(temaId)) {
				komentariTeme.put(entry.getKey(), entry.getValue());
			}
		}
		return komentariTeme;
	}
}
