package rs.xml.oglas.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewCenovnikDTO {

	@NotNull
	@Min(value = 0)
	private int cenaZaDan;
	@NotNull
	@Min(value = 0)
	private int cenaPoKilometru;
	@NotNull
	@Min(value = 0)
	private int cenaOsiguranja;
	@NotNull
	@Min(value = 0)
	@Max(value = 100)
	private int popust;
	@NotNull
	@Min(value = 0)
	private int zaViseOd; // za vise od koliko dana vazi popust
	@NotNull
	private String name;
	
	public NewCenovnikDTO() {
		// TODO Auto-generated constructor stub
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
