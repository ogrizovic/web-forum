package webprog.forum.model;

import java.util.List;

public class Podforum implements IDInterface {

	private String id;
	private String naziv;
	private String opis;
	private byte[] ikonica;
	private String pravila;
	
	private User odgovorniModerator;
	private List<User> moderatori;
	
	public Podforum() {
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public byte[] getIkonica() {
		return ikonica;
	}

	public void setIkonica(byte[] ikonica) {
		this.ikonica = ikonica;
	}

	public String getPravila() {
		return pravila;
	}

	public void setPravila(String pravila) {
		this.pravila = pravila;
	}

	public User getOdgovorniModerator() {
		return odgovorniModerator;
	}

	public void setOdgovorniModerator(User odgovorniModerator) {
		this.odgovorniModerator = odgovorniModerator;
	}

	public List<User> getModeratori() {
		return moderatori;
	}

	public void setModeratori(List<User> moderatori) {
		this.moderatori = moderatori;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
