package webprog.forum.model;

public class Poruka implements IDInterface {

	private String id;
	private User posiljalac;
	private User primalac;
	private String sadrzaj;
	private boolean procitana;
	
	public Poruka() {
	}

	public User getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(User posiljalac) {
		this.posiljalac = posiljalac;
	}

	public User getPrimalac() {
		return primalac;
	}

	public void setPrimalac(User primalac) {
		this.primalac = primalac;
	}

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public boolean isProcitana() {
		return procitana;
	}

	public void setProcitana(boolean procitana) {
		this.procitana = procitana;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
