package rs.xml.oglas.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewOglasDTO {
	
	@NotNull
	private String mesto;
	@NotNull
	private String marka;
	@NotNull
	private String model;
	@NotNull
	private String gorivo;
	@NotNull
	private String menjac;
	@NotNull
	private String klasa;
	//@NotNull // cena ce se kasnije izracunavati
	private int cena; // cena za dan + cena za osiguranje ako postoji
	@NotNull
	private String cenovnik; // ovo treba vremenom promeniti
	@NotNull
	@Min(value = 0)
	private int kilometraza;
	@NotNull
	@Min(value = 0)
	private int planiranaKilometraza;
	@NotNull
	private boolean osiguranje;
	@NotNull
	@Max(value = 4)
	@Min(value = 0)// ovo ne moze za double i float zbog "rounding errors"
	private int brSedistaZaDecu;
	@NotNull
	private Date Od;
	@NotNull
	private Date Do;
	@NotNull
	private List<SlikaDTO> slike = new ArrayList<SlikaDTO>();
	
	public NewOglasDTO() {
		// TODO Auto-generated constructor stub
	}
	
		
	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
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

	public String getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(String cenovnik) {
		this.cenovnik = cenovnik;
	}

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

	public Date getOD() {
		return Od;
	}

	public void setOD(Date Od) {
		this.Od = Od;
	}

	public Date getDO() {
		return Do;
	}

	public void setDO(Date Do) {
		this.Do = Do;
	}

	public List<SlikaDTO> getSlike() {
		return slike;
	}

	public void setSlike(List<SlikaDTO> slike) {
		this.slike = slike;
	}
	
	
	
}
