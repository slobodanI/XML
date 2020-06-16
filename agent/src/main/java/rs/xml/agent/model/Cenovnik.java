package rs.xml.agent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import rs.xml.agent.dto.NewCenovnikDTO;

@Entity
@Table(name = "CENOVNIK")
public class Cenovnik {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="name", unique = true)
	private String name;
	
	@Column(name="cena_za_dan")
	private int cenaZaDan;
	
	@Column(name="cena_po_kilometru")
	private int cenaPoKilometru;
	
	@Column(name="cena_osiguranja")
	private int cenaOsiguranja;
	
	@Column(name="popust")
	private int popust;
	
	@Column(name="za_vise_d")
	private int zaViseOd; // za vise od koliko dana vazi popust
	
	@Column(name="userId")
	private String username;
	
	public Cenovnik() {
		// TODO Auto-generated constructor stub
	}

	public Cenovnik(int cenaZaDan, int cenaPoKilometru, int cenaOsiguranja, int popust, int zaViseOd, String username, String name) {
		super();
		this.cenaZaDan = cenaZaDan;
		this.cenaPoKilometru = cenaPoKilometru;
		this.cenaOsiguranja = cenaOsiguranja;
		this.popust = popust;
		this.zaViseOd = zaViseOd;
		this.username = username;
		this.name = name;
	}
	
	public Cenovnik(NewCenovnikDTO newcenovnikDTO, String username) {
		this.cenaZaDan = newcenovnikDTO.getCenaZaDan();
		this.cenaPoKilometru = newcenovnikDTO.getCenaPoKilometru();
		this.cenaOsiguranja = newcenovnikDTO.getCenaOsiguranja();
		this.popust = newcenovnikDTO.getPopust();
		this.zaViseOd = newcenovnikDTO.getZaViseOd();
		this.name = username + "-" +newcenovnikDTO.getName();
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCenaZaDan() {
		return cenaZaDan;
	}

	public void setCenaZaDan(int cenaZaDan) {
		this.cenaZaDan = cenaZaDan;
	}

	public int getCenaPoKilometru() {
		return cenaPoKilometru;
	}

	public void setCenaPoKilometru(int cenaPoKilometru) {
		this.cenaPoKilometru = cenaPoKilometru;
	}

	public int getCenaOsiguranja() {
		return cenaOsiguranja;
	}

	public void setCenaOsiguranja(int cenaOsiguranja) {
		this.cenaOsiguranja = cenaOsiguranja;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getZaViseOd() {
		return zaViseOd;
	}

	public void setZaViseOd(int zaViseOd) {
		this.zaViseOd = zaViseOd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
