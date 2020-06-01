package rs.xml.oglas.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Slika;

public class OglasDTO {
	
	private Long id;
	
	private Long agendId;
	
	private String marka;
	
	private String model;
	
	private String gorivo;
	
	private String menjac;
	
	private String klasa;
	
	private int cena; // cena za dan + cena za osiguranje ako postoji
	
	private String cenovnik; // ovo treba vremenom promeniti
	
	private int kilometraza;
	
	private int planiranaKilometraza;
	
	private boolean osiguranje;
	
	private int brSedistaZaDecu;
	
	private Date Od;
	
	private Date Do;
	
	private List<SlikaDTO> slike = new ArrayList<SlikaDTO>();
	
	public OglasDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public OglasDTO(Oglas o) {
		this.id=o.getId();
		this.agendId=o.getAgentID();
		this.marka=o.getMarka();
		this.model=o.getModel();
		this.gorivo=o.getGorivo();
		this.menjac=o.getMenjac();
		this.klasa=o.getKlasa();
		this.cena=o.getCena();
		this.cenovnik=null;
		this.kilometraza=o.getKilometraza();
		this.planiranaKilometraza=o.getPlaniranaKilometraza();
		this.osiguranje=o.isOsiguranje();
		this.brSedistaZaDecu=o.getSedistaZaDecu();
		this.Od=o.getOd();
		this.Do=o.getDo();
		for (Slika slika : o.getSlike()) {
			String imageString;

			Encoder encoder = Base64.getEncoder();
			// radi i ako se kaze data:image/png
			imageString = encoder.encodeToString(slika.getSlika());
			SlikaDTO slikaDTO = new SlikaDTO();
			slikaDTO.setSlika("data:image/jpeg;base64," + imageString);
			this.slike.add(slikaDTO);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAgendId() {
		return agendId;
	}

	public void setAgendId(Long agendId) {
		this.agendId = agendId;
	}
	
	
}
