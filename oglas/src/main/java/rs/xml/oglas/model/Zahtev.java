package rs.xml.oglas.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import rs.xml.oglas.dto.NewZahtevDTO;

@Entity
@Table(name = "ZAHTEV")
public class Zahtev {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "OGLAS_ZAHTEV",
            joinColumns = @JoinColumn(name = "zahtev_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "oglas_id", referencedColumnName = "id"))
	private Set<Oglas> oglasi = new HashSet<Oglas>();
	
	@Enumerated(EnumType.STRING)
	private ZahtevStatus status;
	
	@Column(name="agentId")
	private Long agentId;
	
	@Column(name="Od")
	private Date Od;
	
	@Column(name="Do")
	private Date Do;
	
	@Column(name="ocenjen")
	private boolean ocenjen; // da li je ocenjen, 
	
	@Column(name="izvestaj")
	private boolean izvestaj; // da li je kreiran izvestaj
	
	@Column(name="podnosilacId")
	private Long podnosilacId;
	
	@Column(name="chatId")
	private Long chatId;
	
	public Zahtev() {
		// TODO Auto-generated constructor stub
	}

	public Zahtev(Set<Oglas> oglasi, ZahtevStatus status, Long agentId, Date od, Date do1, boolean ocenjen,
			boolean izvestaj, Long podnosilacId, Long chatId) {
		super();
		this.oglasi = oglasi;
		this.status = status;
		this.agentId = agentId;
		Od = od;
		Do = do1;
		this.ocenjen = ocenjen;
		this.izvestaj = izvestaj;
		this.podnosilacId = podnosilacId;
		this.chatId = chatId;
	}

	public Zahtev(NewZahtevDTO zahtevDTO) {
		//super();
		this.oglasi=zahtevDTO.getOglasi();
		this.Od=zahtevDTO.getOd();
		this.Do=zahtevDTO.getDo();
		this.agentId=zahtevDTO.getAgentId();
		this.status=ZahtevStatus.PENDING;
		this.ocenjen=false;
		this.izvestaj=false;
		this.podnosilacId=null;
		this.chatId=null;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Oglas> getOglasi() {
		return oglasi;
	}

	public void setOglasi(Set<Oglas> oglasi) {
		this.oglasi = oglasi;
	}

	public ZahtevStatus getStatus() {
		return status;
	}

	public void setStatus(ZahtevStatus status) {
		this.status = status;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
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

	public boolean isOcenjen() {
		return ocenjen;
	}

	public void setOcenjen(boolean ocenjen) {
		this.ocenjen = ocenjen;
	}

	public boolean isIzvestaj() {
		return izvestaj;
	}

	public void setIzvestaj(boolean izvestaj) {
		this.izvestaj = izvestaj;
	}

	public Long getPodnosilacId() {
		return podnosilacId;
	}

	public void setPodnosilacId(Long podnosilacId) {
		this.podnosilacId = podnosilacId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}
	
	
	
}
