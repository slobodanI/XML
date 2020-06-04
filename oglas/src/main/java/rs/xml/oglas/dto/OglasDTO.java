package rs.xml.oglas.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;

import rs.xml.oglas.model.Oglas;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;

public class OglasDTO {
	
	private Long id;
	
	private String username;
	
	private String marka;
	
	private String model;
	
	private String gorivo;
	
	private String menjac;
	
	private String klasa;
	
	private String mesto;
	
	private int cena; // cena za dan + cena za osiguranje ako postoji
	
//	private String cenovnik; // ovo treba vremenom promeniti
	
	private int kilometraza;
	
	private int planiranaKilometraza;
	
	private boolean osiguranje;
	
	private int brSedistaZaDecu;
	
	private Date Od;
	
	private Date Do;
	
	private List<SlikaDTO> slike = new ArrayList<SlikaDTO>();
	
	private boolean deleted;
	
	public OglasDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public OglasDTO(Oglas o) {
		this.id = o.getId();
		this.username=o.getUsername();
		this.marka = o.getMarka();
		this.model = o.getModel();
		this.menjac = o.getMenjac();
		this.klasa = o.getKlasa();
		this.mesto = o.getMesto();
		this.gorivo = o.getGorivo();
		this.cena = o.getCena();
		this.kilometraza = o.getKilometraza();
		this.planiranaKilometraza= o.getPlaniranaKilometraza();
		this.osiguranje = o.isOsiguranje();
		this.brSedistaZaDecu = o.getSedistaZaDecu();
		this.Od = o.getOd();
		this.Do = o.getDo();
		this.deleted = o.isDeleted();
		for(Slika s: o.getSlike()) {		
			String imageString;
			Encoder encoder = Base64.getEncoder();
			// radi i ako se kaze data:image/png
			imageString = encoder.encodeToString(s.getSlika());
			SlikaDTO slikaDTO = new SlikaDTO();
			slikaDTO.setSlika("data:image/jpeg;base64," + imageString);
			this.getSlike().add(slikaDTO);
		}
	}
	
	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGorivo() {
		return gorivo;
	}

	public void setGorivo(String gorivo) {
		this.gorivo = gorivo;
	}

	public String getMenjac() {
		return menjac;
	}

	public void setMenjac(String menjac) {
		this.menjac = menjac;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}

//	public String getCenovnik() {
//		return cenovnik;
//	}
//
//	public void setCenovnik(String cenovnik) {
//		this.cenovnik = cenovnik;
//	}

	public int getKilometraza() {
		return kilometraza;
	}

	public void setKilometraza(int kilometraza) {
		this.kilometraza = kilometraza;
	}

	public int getPlaniranaKilometraza() {
		return planiranaKilometraza;
	}

	public void setPlaniranaKilometraza(int planiranaKilometraza) {
		this.planiranaKilometraza = planiranaKilometraza;
	}

	public boolean isOsiguranje() {
		return osiguranje;
	}

	public void setOsiguranje(boolean osiguranje) {
		this.osiguranje = osiguranje;
	}

	public int getBrSedistaZaDecu() {
		return brSedistaZaDecu;
	}

	public void setBrSedistaZaDecu(int brSedistaZaDecu) {
		this.brSedistaZaDecu = brSedistaZaDecu;
	}

	public List<SlikaDTO> getSlike() {
		return slike;
	}

	public void setSlike(List<SlikaDTO> slike) {
		this.slike = slike;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public Date getOd() {
		return Od;
	}

	public void setOd(Date od) {
		Od = od;
	}

	public Date getDo() {
		return Do;
	}

	public void setDo(Date do1) {
		Do = do1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
