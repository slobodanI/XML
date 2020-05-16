package rs.xml.oglas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CENOVNIK")
public class Cenovnik {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name="cenaZaDan")
	private int cenaZaDan;
	
	@Column(name="cenaPoKilometru")
	private int cenaPoKilometru;
	
	@Column(name="cenaOsiguranja")
	private int cenaOsiguranja;
	
	@Column(name="popust")
	private int popust;
	
	@Column(name="zaViseOd")
	private int zaViseOd; // za vise od koliko dana vazi popust
	
	@Column(name="userId")
	private Long userId;
	
	public Cenovnik() {
		// TODO Auto-generated constructor stub
	}

	public Cenovnik(int cenaZaDan, int cenaPoKilometru, int cenaOsiguranja, int popust, int zaViseOd, Long userId) {
		super();
		this.cenaZaDan = cenaZaDan;
		this.cenaPoKilometru = cenaPoKilometru;
		this.cenaOsiguranja = cenaOsiguranja;
		this.popust = popust;
		this.zaViseOd = zaViseOd;
		this.userId = userId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
