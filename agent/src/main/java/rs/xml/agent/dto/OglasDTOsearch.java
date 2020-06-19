package rs.xml.agent.dto;

import java.sql.Date;
import java.util.Base64;
import java.util.Base64.Encoder;

import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Slika;

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
    double ocena;
    
    public OglasDTOsearch() {
		// TODO Auto-generated constructor stub
	}
    
    public OglasDTOsearch(Oglas oglas) {
    	this.setId(oglas.getId());
		this.setMesto(oglas.getMesto());
		this.setMarka(oglas.getMarka());
		this.setModel(oglas.getModel());
		this.setMenjac(oglas.getMenjac());
		this.setGorivo(oglas.getGorivo());
		this.setKlasa(oglas.getKlasa());
		this.setPredjenaInt(oglas.getKilometraza());
		this.setPlaniranaInt(oglas.getPlaniranaKilometraza());
		this.setOsiguranjeBool(oglas.isOsiguranje());
		this.setBrSedZaDecuInt(oglas.getSedistaZaDecu());
		this.setOdDate(oglas.getOd());
		this.setDoDate(oglas.getDo());
		
		if(oglas.getSlike().isEmpty()) {
			this.setSlika(null);
		} else {
			Encoder encoder = Base64.getEncoder();
			String imageString = "";
			for(Slika slika : oglas.getSlike()) {
				imageString = encoder.encodeToString(slika.getSlika());
				break;
			}
//			imageString = encoder.encodeToString(oglas.getSlike().get(0).getSlika());
			this.setSlika("data:image/jpeg;base64," + imageString);
		}
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

	public double getOcena() {
		return ocena;
	}

	public void setOcena(double ocena) {
		this.ocena = ocena;
	}
    
    
}
