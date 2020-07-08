package rs.xml.oglas.model;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
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

	@Column(name="zid")
	private String zid;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "OGLAS_ZAHTEV",
            joinColumns = @JoinColumn(name = "zahtev_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "oglas_id", referencedColumnName = "id"))
	private Set<Oglas> oglasi = new HashSet<Oglas>();
	
	@Enumerated(EnumType.STRING)
	private ZahtevStatus status;
	
	@Column(name="username")
	private String username;
	
	@Column(name="Od")
	private Date Od;
	
	@Column(name="Do")
	private Date Do;
	
	@Column(name="vreme_podnosenja")
	private Date vremePodnosenja;
	
	@Column(name="ocenjen")
	private boolean ocenjen; // da li je ocenjen, 
	
	@Column(name="izvestaj")
	private boolean izvestaj; // da li je kreiran izvestaj
	
	@Column(name="podnosilac_username")
	private String podnosilacUsername;
	
	@Column(name="chatId")
	private String chatId;
	
	public Zahtev() {
		// TODO Auto-generated constructor stub
	}

	public Zahtev(Set<Oglas> oglasi, ZahtevStatus status, String username, Date od, Date do1, boolean ocenjen,
			boolean izvestaj, String podnosilacUsername, String chatId) {
		super();
		this.oglasi = oglasi;
		this.status = status;
		this.username = username;
		Od = od;
		Do = do1;
		long millis=System.currentTimeMillis();  
	    Date date=new Date(millis); 
		vremePodnosenja = date;
		this.ocenjen = ocenjen;
		this.izvestaj = izvestaj;
		this.podnosilacUsername = podnosilacUsername;
		this.chatId = chatId;
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

	

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getPodnosilacUsername() {
		return podnosilacUsername;
	}

	public void setPodnosilacUsername(String podnosilacUsername) {
		this.podnosilacUsername = podnosilacUsername;
	}

	public Date getVremePodnosenja() {
		return vremePodnosenja;
	}

	public void setVremePodnosenja(Date vremePodnosenja) {
		this.vremePodnosenja = vremePodnosenja;
	}

	public String getZid() {
		return zid;
	}

	public void setZid(String zid) {
		this.zid = zid;
	}
	
	
}
