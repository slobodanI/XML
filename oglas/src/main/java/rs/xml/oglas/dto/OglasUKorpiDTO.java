package rs.xml.oglas.dto;

import java.sql.Date;

public class OglasUKorpiDTO {
	
	private Long oglasId;
	private Date Od;
	private Date Do;
	
	public OglasUKorpiDTO() {
	// TODO Auto-generated constructor stub
	}

	public Long getOglasId() {
		return oglasId;
	}

	public void setOglasId(Long oglasId) {
		this.oglasId = oglasId;
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
	

}
