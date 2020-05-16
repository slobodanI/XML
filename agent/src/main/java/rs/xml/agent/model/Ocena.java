package rs.xml.agent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCENA")
public class Ocena {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	
	@Column(name = "ocena")
	private int ocena;
	
	@Column(name = "komentar")
	private String komentar;
	
	@Column(name = "approved")
	private boolean approved;
	
	@Column(name = "odgovor")
	private String odgovor; // od agenta
	
	@Column(name = "oglasId")
	private Long oglasId;
	
	public Ocena() {
		// TODO Auto-generated constructor stub
	}

	public Ocena(int ocena, String komentar, boolean approved, String odgovor, Long oglasId) {
		super();
		this.ocena = ocena;
		this.komentar = komentar;
		this.approved = approved;
		this.odgovor = odgovor;
		this.oglasId = oglasId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOcena() {
		return ocena;
	}

	public void setOcena(int ocena) {
		this.ocena = ocena;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
	}

	public Long getOglasId() {
		return oglasId;
	}

	public void setOglasId(Long oglasId) {
		this.oglasId = oglasId;
	}
	
	
}
