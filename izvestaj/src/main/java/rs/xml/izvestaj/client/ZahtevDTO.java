package rs.xml.izvestaj.client;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class ZahtevDTO {
	
	private String username;
	private ZahtevStatus status;
	private Date od;
	private Date do1;
	private boolean ocenjen;
	private boolean izvestaj;
	private String podnosilacUsername;
	private Long chatId;
	private Set<OglasDTOsearch> oglasi =  new HashSet<OglasDTOsearch>();
	
	public ZahtevDTO() {
		
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

	public Long getChatId() {
		return chatId;
	}
	public void setChatId(Long chatId) {
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
