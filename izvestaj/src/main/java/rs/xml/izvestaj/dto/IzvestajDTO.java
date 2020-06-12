package rs.xml.izvestaj.dto;

import rs.xml.izvestaj.model.Izvestaj;

public class IzvestajDTO {
	
	private Long id;
	private Long oglasId;
	private int predjeniKilometri;
	private String tekst;
	private Long zahtevId;
	
	public IzvestajDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public IzvestajDTO(Izvestaj i) {
		this.id=i.getId();
		this.oglasId=i.getOglasId();
		this.predjeniKilometri=i.getPredjeniKilometri();
		this.tekst=i.getTekst();
		this.zahtevId=i.getZahtevId();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
