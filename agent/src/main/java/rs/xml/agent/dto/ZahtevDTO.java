package rs.xml.agent.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import rs.xml.agent.model.Oglas;
import rs.xml.agent.model.Zahtev;
import rs.xml.agent.model.ZahtevStatus;

public class ZahtevDTO {
	
	private Long id;
	private String username;
	private ZahtevStatus status;
	private Date od;
	private Date do1;
	private boolean ocenjen;
	private boolean izvestaj;
	private String podnosilacUsername;
	private String chatId;
	private Set<OglasDTOsearch> oglasi =  new HashSet<OglasDTOsearch>();
	
	public ZahtevDTO() {
		
	}
	
	public ZahtevDTO(Zahtev z) {
		super();
		this.id = z.getId();
		this.username=z.getUsername();
		this.status=z.getStatus();
		this.od=z.getOd();
		this.do1=z.getDo();
		this.ocenjen=z.isOcenjen();
		this.izvestaj=z.isIzvestaj();
		this.podnosilacUsername=z.getPodnosilacUsername();
		this.chatId=z.getChatId();
		Set<OglasDTOsearch> oglasi= new HashSet<OglasDTOsearch>();
		for(Oglas o : z.getOglasi()) {
			oglasi.add(new OglasDTOsearch(o));
		}
		this.oglasi=oglasi;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ZahtevStatus getStatus() {
		return status;
	}
	public void setStatus(ZahtevStatus status) {
		this.status = status;
	}
	public Date getOd() {
		return od;
	}
	public void setOd(Date od) {
		this.od = od;
	}
	public Date getDo1() {
		return do1;
	}
	public void setDo1(Date do1) {
		this.do1 = do1;
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

	public Set<OglasDTOsearch> getOglasi() {
		return oglasi;
	}

	public void setOglasi(Set<OglasDTOsearch> oglasi) {
		this.oglasi = oglasi;
	}

	public String getPodnosilacUsername() {
		return podnosilacUsername;
	}

	public void setPodnosilacUsername(String podnosilacUsername) {
		this.podnosilacUsername = podnosilacUsername;
	}


	

}
