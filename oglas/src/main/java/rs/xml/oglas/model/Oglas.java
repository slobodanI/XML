package rs.xml.oglas.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rs.xml.oglas.dto.NewOglasDTO;
import rs.xml.oglas.dto.SlikaDTO;

@Entity
@Table(name = "OGLAS")
public class Oglas {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="oid")
	private String oid;
	
	@Column(name="mesto")
	private String mesto;
	
	@Column(name="marka")
	private String marka;
	
	@Column(name="model")
	private String model;
	
	@Column(name="gorivo")
	private String gorivo;
	
	@Column(name="menjac")
	private String menjac;
	
	@Column(name="klasa")
	private String klasa;
	
	@Column(name="cena")
	private int cena;
	
	@OneToOne
	private Cenovnik cenovnik;
	
	@Column(name="kilometraza")
	private int kilometraza;
	
	@Column(name="planirana_kilometraza")
	private int planiranaKilometraza;
	
	@Column(name="sedista_za_decu")
	private int sedistaZaDecu;
	
	@OneToMany(mappedBy = "oglas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Slika> slike = new HashSet<Slika>();

	@Column(name="osiguranje")
	private boolean osiguranje;
	
	@Column(name="username")
	private String username;
	
	@Column(name="Od")
	private Date Od;
	
	@Column(name="Do")
	private Date Do;
	
	@Column(name="deleted")
	private boolean deleted;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "OGLAS_ZAHTEV",
            joinColumns = @JoinColumn(name = "oglas_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "zahtev_id", referencedColumnName = "id"))
	private List<Zahtev> zahtevi = new ArrayList<Zahtev>();
	
	public Oglas() {
		// TODO Auto-generated constructor stub
	}

	public Oglas(String mesto, String marka, String model, String gorivo, String menjac, String klasa, int cena, Cenovnik cenovnik,
			int kilometraza, int planiranaKilometraza, int sedistaZaDecu, HashSet<Slika> slike, boolean osiguranje,
			String username, Date od, Date do1, List<Zahtev> zahtevi) {
		super();
		this.mesto = mesto; 
		this.marka = marka;
		this.model = model;
		this.gorivo = gorivo;
		this.menjac = menjac;
		this.klasa = klasa;
		this.cena = cena;
		this.cenovnik = cenovnik;
		this.kilometraza = kilometraza;
		this.planiranaKilometraza = planiranaKilometraza;
		this.sedistaZaDecu = sedistaZaDecu;
		this.slike = slike;
		this.osiguranje = osiguranje;
		this.username = username;
		this.Od = od;
		this.Do = do1;
		this.deleted = false; // novi oglas nije obrisan
		this.zahtevi = zahtevi;
	}
	
	public Oglas(NewOglasDTO oglasDTO) {
		super();
//		this.mesto = oglasDTO.getMesto();
//		this.marka = oglasDTO.getMarka();
//		this.model = oglasDTO.getModel();
//		this.gorivo = oglasDTO.getGorivo();
//		this.menjac = oglasDTO.getMenjac();
//		this.klasa = oglasDTO.getKlasa();
		this.cena = oglasDTO.getCena();
		this.cenovnik = null;//oglasDTO.getCenovnik();
		this.kilometraza = oglasDTO.getKilometraza();
		this.planiranaKilometraza = oglasDTO.getPlaniranaKilometraza();
		this.sedistaZaDecu = oglasDTO.getBrSedistaZaDecu();
		
		for(SlikaDTO slikaDTO: oglasDTO.getSlike()) {
			//KADA UPISUJES U BAZU SKLONI 'data:image/jpeg;base64,' a kad vracas sliku dodaj 'data:image/jpeg;base64,'			
			//System.out.println("SRC SLIKE :"+slikaDTO.slika()); // data:image/jpeg;base64,/9j/..... split na ,
			String split[] = slikaDTO.getSlika().split(",");
			//slikaDTO.setSlika(split[1]);
			
			byte[] imageByte;
			Decoder decoder = Base64.getDecoder();
	        imageByte = decoder.decode(split[1]);
			
			Slika slika = new Slika();
			slika.setOglas(this);
			slika.setSlika(imageByte);
			this.slike.add(slika);
		}
		
		
		
		this.osiguranje = oglasDTO.isOsiguranje();
		this.username = null;
		this.Od = oglasDTO.getOD();
		this.Do = oglasDTO.getDO();
		this.deleted = false; // novi oglas nije obrisan
		
	}
	
	public Long getId() {
		return id;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Cenovnik getCenovnik() {
		return cenovnik;
	}

	public void setCenovnik(Cenovnik cenovnik) {
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

	public int getSedistaZaDecu() {
		return sedistaZaDecu;
	}

	public void setSedistaZaDecu(int sedistaZaDecu) {
		this.sedistaZaDecu = sedistaZaDecu;
	}

	public Set<Slika> getSlike() {
		return slike;
	}

	public void setSlike(Set<Slika> slike) {
		this.slike = slike;
	}

	public boolean isOsiguranje() {
		return osiguranje;
	}

	public void setOsiguranje(boolean osiguranje) {
		this.osiguranje = osiguranje;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	
	
}
