package rs.xml.oglas.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.model.ZahtevStatus;

public class ZahtevDTO {
	
	private String username;
	private ZahtevStatus status;
	private Date od;
	private Date do1;
	private boolean ocenjen;
	private boolean izvestaj;
	private Long podnosilacId;
	private Long chatId;
	private Set<OglasDTO> oglasi =  new HashSet<OglasDTO>();
	
	public ZahtevDTO() {
		
	}
	
	public ZahtevDTO(Zahtev z) {
		super();
		this.username=z.getUsername();
		this.status=z.getStatus();
		this.od=z.getOd();
		this.do1=z.getDo();
		this.ocenjen=z.isOcenjen();
		this.izvestaj=z.isIzvestaj();
		this.podnosilacId=z.getPodnosilacId();
		this.chatId=z.getChatId();
		Set<OglasDTO> oglasi= new HashSet<OglasDTO>();
		for(Oglas o : z.getOglasi()) {
			oglasi.add(new OglasDTO(o));
		}
		this.oglasi=oglasi;
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

	public Set<OglasDTO> getOglasi() {
		return oglasi;
	}

	public void setOglasi(Set<OglasDTO> oglasi) {
		this.oglasi = oglasi;
	}
	

}
