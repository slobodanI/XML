package rs.xml.agent.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CenovnikUpdateDTO {

	@Min(value = 0)
	private int cenaZaDan;
	@Min(value = 0)
	private int cenaPoKilometru;
	@Min(value = 0)
	private int cenaOsiguranja;
	@Min(value = 0)
	@Max(value = 100)
	private int popust;
	@Min(value = 0)
	private int zaViseOd; // za vise od koliko dana vazi popust
	private String name;
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
