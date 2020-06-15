package rs.xml.agent.dto;

import java.util.List;

public class KorpaDTO {

	private boolean bundle;
	private List<OglasUKorpiDTO> oglasi;
	
	public KorpaDTO() {
		
	}
	
	
	public boolean isBundle() {
		return bundle;
	}
	public void setBundle(boolean bundle) {
		this.bundle = bundle;
	}


	public List<OglasUKorpiDTO> getOglasi() {
		return oglasi;
	}


	public void setOglasi(List<OglasUKorpiDTO> oglasi) {
		this.oglasi = oglasi;
	}

	
	
	
	
}
