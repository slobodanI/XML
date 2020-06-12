package rs.xml.oglas.dto;

import javax.validation.constraints.NotNull;

public class NewIzvestajDTO {
	
	
	@NotNull
	private Long oglasId;
	
	@NotNull
	private int predjeniKilometri;
	
	private String tekst;
	
	@NotNull
	private Long zahtevId;
	
	public NewIzvestajDTO() {
		// TODO Auto-generated constructor stub
	}


	public Long getOglasId() {
		return oglasId;
	}

	public void setOglasId(Long oglasId) {
		this.oglasId = oglasId;
	}

	public int getPredjeniKilometri() {
		return predjeniKilometri;
	}

	public void setPredjeniKilometri(int predjeniKilometri) {
		this.predjeniKilometri = predjeniKilometri;
	}

	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public Long getZahtevId() {
		return zahtevId;
	}

	public void setZahtevId(Long zahtevId) {
		this.zahtevId = zahtevId;
	}
	
}
