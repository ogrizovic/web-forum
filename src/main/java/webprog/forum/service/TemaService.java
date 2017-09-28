package webprog.forum.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import webprog.forum.model.Tema;
import webprog.forum.repo.TemaRepo;

public class TemaService extends DefaultCrudService<Tema> {

	
	public TemaService() {
		super(new TemaRepo());
	}
	
	public Map<String, Tema> getTemePodforuma(String podforumId) {
		return ((TemaRepo) getRepo()).getAllForPodforum(podforumId);
	}
	
	public void removeTemePodforuma(String podforumId) {
		((TemaRepo) getRepo()).deleteAllForPodforum(podforumId);
	}

	public void saveImage(InputStream uploadedInputStream, String uploadedFileLocation) {
		
		try {
			
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		
			int read = 0;
			byte[] bytes = new byte[3072];
			
			while((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
