package webprog.forum.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Komentar implements IDInterface {

	private String id;
	private Tema parentTema;
	private User autor;
	private String datumKomentara;
	private Komentar parentKomentar;
	private List<String> podkomentari;
	private String tekstKomentara;
	private int likes;
	private int dislikes;
	private boolean izmenjen;
	private boolean obrisan;
	private List<String> votes;
	

	public Komentar() {
	}
	
	

	public List<String> getVotes() {
		return votes;
	}

	public void setVotes(List<String> votes) {
		this.votes = votes;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	public Tema getParentTema() {
		return parentTema;
	}

	public void setParentTema(Tema parentTema) {
		this.parentTema = parentTema;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public String getDatumKomentara() {
		return datumKomentara;
	}
	
	public Date getDatumKomentaraAsDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			return dateFormat.parse(datumKomentara);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setDatumKomentara(String datumKomentara) {
		this.datumKomentara = datumKomentara;
	}
	
	public void setDatumKomentaraNow() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		this.datumKomentara = dateFormat.format(date);
	}

	public Komentar getParentKomentar() {
		return parentKomentar;
	}

	public void setParentKomentar(Komentar parentKomentar) {
		this.parentKomentar = parentKomentar;
	}

	public List<String> getPodkomentari() {
		return podkomentari;
	}

	public void setPodkomentari(ArrayList<String> podkomentari) {
		this.podkomentari = podkomentari;
	}

	public String getTekstKomentara() {
		return tekstKomentara;
	}

	public void setTekstKomentara(String tekstKomentara) {
		this.tekstKomentara = tekstKomentara;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public boolean isIzmenjen() {
		return izmenjen;
	}

	public void setIzmenjen(boolean izmenjen) {
		this.izmenjen = izmenjen;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
