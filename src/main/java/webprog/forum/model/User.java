package webprog.forum.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import webprog.forum.auth.Role;

public class User implements IDInterface {

	private String username;
	private String password;
	private String ime;
	private String prezime;
	private Role uloga;
	private String telefon;
	private String email;
	private String datumRegistracije;
	private List<String> praceniPodforumi;
	private List<Tema> teme;
	private List<Komentar> komentari;
	
	public User() {
	}
	
	@Override
	public String getId() {
		return username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Role getUloga() {
		return uloga;
	}

	public void setUloga(Role uloga) {
		this.uloga = uloga;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDatumRegistracije() {
		return datumRegistracije;
	}

	public Date getDatumRegistracijeAsDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse(datumRegistracije);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setDatumRegistracije(String datumRegistracije) {
		this.datumRegistracije = datumRegistracije;
	}

	public void setDatumRegistracijeNow() {
		DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		Date date = new Date();
		this.datumRegistracije = dateFormat.format(date);
	}

	public List<String> getPraceniPodforumi() {
		return praceniPodforumi;
	}

	public void setPraceniPodforumi(List<String> praceniPodforumi) {
		this.praceniPodforumi = praceniPodforumi;
	}

	public List<Tema> getTeme() {
		return teme;
	}

	public void setTeme(List<Tema> teme) {
		this.teme = teme;
	}

	public List<Komentar> getKomentari() {
		return komentari;
	}

	public void setKomentari(List<Komentar> komentari) {
		this.komentari = komentari;
	}

	
}
