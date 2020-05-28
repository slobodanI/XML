package rs.xml.oglas.dto;

import java.sql.Date;

public class OglasDTOsearch {

	Long id;
	String mesto;
	Date odDate;
	Date doDate;
	String marka;
	String model;
	String menjac;
	String gorivo;
	String klasa;
	int predjenaInt;
    int planiranaInt;
    boolean osiguranjeBool;
    int brSedZaDecuInt;
    String slika;
    
    public OglasDTOsearch() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public Date getOdDate() {
		return odDate;
	}

	public void setOdDate(Date odDate) {
		this.odDate = odDate;
	}

	public Date getDoDate() {
		return doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
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

	public String getMenjac() {
		return menjac;
	}

	public void setMenjac(String menjac) {
		this.menjac = menjac;
	}

	public String getGorivo() {
		return gorivo;
	}

	public void setGorivo(String gorivo) {
		this.gorivo = gorivo;
	}

	public String getKlasa() {
		return klasa;
	}

	public void setKlasa(String klasa) {
		this.klasa = klasa;
	}

	public int getPredjenaInt() {
		return predjenaInt;
	}

	public void setPredjenaInt(int predjenaInt) {
		this.predjenaInt = predjenaInt;
	}

	public int getPlaniranaInt() {
		return planiranaInt;
	}

	public void setPlaniranaInt(int planiranaInt) {
		this.planiranaInt = planiranaInt;
	}

	public boolean isOsiguranjeBool() {
		return osiguranjeBool;
	}

	public void setOsiguranjeBool(boolean osiguranjeBool) {
		this.osiguranjeBool = osiguranjeBool;
	}

	public int getBrSedZaDecuInt() {
		return brSedZaDecuInt;
	}

	public void setBrSedZaDecuInt(int brSedZaDecuInt) {
		this.brSedZaDecuInt = brSedZaDecuInt;
	}

	public String getSlika() {
		return slika;
	}

	public void setSlika(String slika) {
		this.slika = slika;
	}
    
    
}
