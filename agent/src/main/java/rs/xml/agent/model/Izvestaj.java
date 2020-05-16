package rs.xml.agent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IZVESTAJ")
public class Izvestaj {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	
	@Column(name="oglasId")
	private Long oglasId;
	
	@Column(name="predjeniKilometri")
	private int predjeniKilometri;
	
	@Column(name="tekst")
	private String tekst;
	
	@Column(name="zahtevId")
	private Long zahtevId;
	
	public Izvestaj() {
		// TODO Auto-generated constructor stub
	}

	public Izvestaj(Long oglasId, int predjeniKilometri, String tekst, Long zahtevId) {
		super();
		this.oglasId = oglasId;
		this.predjeniKilometri = predjeniKilometri;
		this.tekst = tekst;
		this.zahtevId = zahtevId;
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
