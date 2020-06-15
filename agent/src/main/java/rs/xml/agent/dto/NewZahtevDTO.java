package rs.xml.agent.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import rs.xml.agent.model.Oglas;

public class NewZahtevDTO {
	
	@NotNull
	private Long podnosilacId;
	
//	@NotNull
//	private Long podnosilacId;
	
	@NotNull
	private Date Od;
	@NotNull
	private Date Do;
	
	private Set<Oglas> oglasi =  new HashSet<Oglas>();
	
	public NewZahtevDTO(Set<Oglas> oglasi,Long podnosilacId,Date od,Date do1) {
		super();
		this.podnosilacId=podnosilacId;
		this.oglasi=oglasi;
		this.Od=od;
		this.Do=do1;
		
	}

	public Long getAgentId() {
		return podnosilacId;
	}

	public void setAgentId(Long agentId) {
		this.podnosilacId = agentId;
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

	public Set<Oglas> getOglasi() {
		return oglasi;
	}

	public void setOglasi(Set<Oglas> oglasi) {
		this.oglasi = oglasi;
	}
	
	
	
	
}
