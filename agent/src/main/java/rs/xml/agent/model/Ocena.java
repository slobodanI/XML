package rs.xml.agent.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import rs.xml.agent.dto.OcenaNewDTO;

@Entity
@Table(name = "OCENA")
public class Ocena {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "oid")
	private String oid;
	
	@Column(name = "ocena")
	private int ocena;
	
	@Column(name = "komentar")
	private String komentar;
	
	@Enumerated(EnumType.STRING)
	private OcenaApprovedStatus approved; // od strane admina
	
	@Column(name = "odgovor")
	private String odgovor; // od agenta
	
	@Column(name = "username_ko")
	private String usernameKo; //username onog koji je dao ocenu
	
	@Column(name = "username_koga")
	private String usernameKoga; //username onog za koga je ocena
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Oglas oglas; // marka: model | marka: model | itd...
	
	@Column(name = "zahtev_id")
	private Long zahtevId;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	public Ocena() {
		// TODO Auto-generated constructor stub
	}
	
	public Ocena(Long id, int ocena, String komentar, OcenaApprovedStatus approved, String odgovor, String usernameKo,
			String usernameKoga, Oglas oglas, Long zahtevId, boolean deleted) {
		this.id = id;
		this.ocena = ocena;
		this.komentar = komentar;
		this.approved = approved;
		this.odgovor = odgovor;
		this.usernameKo = usernameKo;
		this.usernameKoga = usernameKoga;
		this.oglas = oglas;
		this.zahtevId = zahtevId;
		this.deleted = deleted;
	}

	public Ocena(OcenaNewDTO ocenaNewDTO, String usernameKo, String usernameKoga, Oglas oglas) {
		this.ocena = ocenaNewDTO.getOcena();
		this.komentar = ocenaNewDTO.getKomentar();
		this.approved = OcenaApprovedStatus.UNKNOWN;
		this.odgovor = "nema odgovora...";
		this.usernameKo = usernameKo;
		this.usernameKoga = usernameKoga;
		this.oglas = oglas;
		this.zahtevId = ocenaNewDTO.getZahtevId();
		this.deleted = false;
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
	
	public OcenaApprovedStatus getApproved() {
		return approved;
	}

	public void setApproved(OcenaApprovedStatus approved) {
		this.approved = approved;
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
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

	public Oglas getOglas() {
		return oglas;
	}
	
	public void setOglas(Oglas oglas) {
		this.oglas = oglas;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Long getZahtevId() {
		return zahtevId;
	}

	public void setZahtevId(Long zahtevId) {
		this.zahtevId = zahtevId;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	
		
}
