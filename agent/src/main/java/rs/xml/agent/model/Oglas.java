package rs.xml.agent.model;

import java.sql.Date;
import java.util.List;

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

@Entity
@Table(name = "OGLAS")
public class Oglas {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
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
	
	@Column(name="planiranaKilometraza")
	private int planiranaKilometraza;
	
	@Column(name="sedistaZaDecu")
	private int sedistaZaDecu;
	
	@OneToMany(mappedBy = "oglas")
    private List<Slika> slike;
	
	@Column(name="osiguranje")
	private boolean osiguranje;
	
	@Column(name="agentID")
	private Long agentID;
	
	@Column(name="Od")
	private Date Od;
	
	@Column(name="Do")
	private Date Do;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "OGLAS_ZAHTEV",
            joinColumns = @JoinColumn(name = "oglas_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "zahtev_id", referencedColumnName = "id"))
	private List<Zahtev> zahtevi;
	
	public Oglas() {
		// TODO Auto-generated constructor stub
	}

	public Oglas(String marka, String model, String gorivo, String menjac, String klasa, int cena, Cenovnik cenovnik,
			int kilometraza, int planiranaKilometraza, int sedistaZaDecu, List<Slika> slike, boolean osiguranje,
			Long agentID, Date od, Date do1, List<Zahtev> zahtevi) {
		super();
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
		this.agentID = agentID;
		Od = od;
		Do = do1;
		this.zahtevi = zahtevi;
	}

	public Long getId() {
		return id;
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

	public List<Slika> getSlike() {
		return slike;
	}

	public void setSlike(List<Slika> slike) {
		this.slike = slike;
	}

	public boolean isOsiguranje() {
		return osiguranje;
	}

	public void setOsiguranje(boolean osiguranje) {
		this.osiguranje = osiguranje;
	}

	public Long getAgentID() {
		return agentID;
	}

	public void setAgentID(Long agentID) {
		this.agentID = agentID;
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

	public List<Zahtev> getZahtevi() {
		return zahtevi;
	}

	public void setZahtevi(List<Zahtev> zahtevi) {
		this.zahtevi = zahtevi;
	}
	
	
}
