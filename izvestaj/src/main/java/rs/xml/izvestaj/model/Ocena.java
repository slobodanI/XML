package rs.xml.izvestaj.model;

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
	private boolean approved; // od strane admina
	
	@Column(name = "odgovor")
	private String odgovor; // od agenta
	
	@Column(name = "zahtev_id")
	private Long zahtevId;
	
	@Column(name = "username_ko")
	private String usernameKo; //username onog koji je dao ocenu
	
	@Column(name = "username_koga")
	private String usernameKoga; //username onog za koga je ocena
	
	@Column(name = "oglasi")
	private String oglasi; // marka: model | marka: model | itd...
	
	@Column(name = "deleted")
	private boolean deleted;
	
	public Ocena() {
		// TODO Auto-generated constructor stub
	}

	public Ocena(int ocena, String komentar, boolean approved, String odgovor, Long oglasId, boolean deleted) {
		super();
		this.ocena = ocena;
		this.komentar = komentar;
		this.approved = approved;
		this.odgovor = odgovor;
		this.zahtevId = oglasId;
		this.deleted = deleted;
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

	public Long getZahtevId() {
		return zahtevId;
	}

	public void setZahtevId(Long zahtevId) {
		this.zahtevId = zahtevId;
	}

	public String getUsernameKo() {
		return usernameKo;
	}

	public void setUsernameKo(String usernameKo) {
		this.usernameKo = usernameKo;
	}

	public String getUsernameKoga() {
		return usernameKoga;
	}

	public void setUsernameKoga(String usernameKoga) {
		this.usernameKoga = usernameKoga;
	}

	public String getOglasi() {
		return oglasi;
	}

	public void setOglasi(String oglasi) {
		this.oglasi = oglasi;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	
}
