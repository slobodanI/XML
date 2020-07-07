package rs.xml.oglas.dto;

import rs.xml.oglas.model.Cenovnik;

public class CenovnikDTO {

	private Long id;
	private int cenaZaDan;
	private int cenaPoKilometru;
	private int cenaOsiguranja;
	private int popust;
	private int zaViseOd; // za vise od koliko dana vazi popust
	private String name;

	public CenovnikDTO(Cenovnik cen) {
		this.id = cen.getId();
		this.cenaZaDan = cen.getCenaZaDan();
		this.cenaPoKilometru = cen.getCenaPoKilometru();
		this.cenaOsiguranja = cen.getCenaOsiguranja();
		this.popust = cen.getPopust();
		this.zaViseOd = cen.getZaViseOd();
		this.name = cen.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCenaZaDan() {
		return cenaZaDan;
	}

	public void setCenaZaDan(int cenaZaDan) {
		this.cenaZaDan = cenaZaDan;
	}

	public int getCenaPoKilometru() {
		return cenaPoKilometru;
	}

	public void setCenaPoKilometru(int cenaPoKilometru) {
		this.cenaPoKilometru = cenaPoKilometru;
	}

	public int getCenaOsiguranja() {
		return cenaOsiguranja;
	}

	public void setCenaOsiguranja(int cenaOsiguranja) {
		this.cenaOsiguranja = cenaOsiguranja;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getZaViseOd() {
		return zaViseOd;
	}

	public void setZaViseOd(int zaViseOd) {
		this.zaViseOd = zaViseOd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
