package webprog.forum.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tema implements IDInterface {

	private String id;
	private Podforum parentPodforum;
	private String naslov;	// jedinstven u okviru podforuma
	private TipTeme tipTeme;
	private String autor;
	private List<String> komentari;
	private String sadrzaj; // Da li je string?
	private String datumKreiranja;
	private int likes;
	private int dislikes;
	private List<String> votes; // oni koji su lajkovali/dislajkovali
	
	public Tema() {
	}
	
	public void incrementLikes() {
		++likes;
	}
	
	public void incrementDislikes() {
		++dislikes;
	}

	public Podforum getParentPodforum() {
		return parentPodforum;
	}

	public void setParentPodforum(Podforum parentPodforum) {
		this.parentPodforum = parentPodforum;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public TipTeme getTipTeme() {
		return tipTeme;
	}

	public void setTipTeme(TipTeme tipTeme) {
		this.tipTeme = tipTeme;
	}

//	public User getAutor() {
//		return autor;
//	}
//
//	public void setAutor(User autor) {
//		this.autor = autor;
//	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public List<String> getKomentari() {
		return komentari;
	}

	public void setKomentari(List<String> komentari) {
		this.komentari = komentari;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public String getDatumKreiranja() {
		return datumKreiranja;
	}
	
	public Date getDatumKreiranjaAsDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		try {
			return dateFormat.parse(datumKreiranja);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setDatumKreiranja(String datumKreiranja) {
		this.datumKreiranja = datumKreiranja;
	}
	
	public void setDatumKreiranjaNow() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
//		ZonedDateTime date = ZonedDateTime.now();
//		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.datumKreiranja = dateFormat.format(date);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getVotes() {
		return votes;
	}

	public void setVotes(ArrayList<String> arrayList) {
		this.votes = arrayList;
	}

	
	
}
