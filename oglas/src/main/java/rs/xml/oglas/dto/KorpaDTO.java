package rs.xml.oglas.dto;

import java.util.List;
import java.util.Set;

import rs.xml.oglas.model.Oglas;

public class KorpaDTO {

	private boolean bundle;
	private List<OglasDTO> oglasi;
	
	public KorpaDTO() {
		
	}
	
	
	public boolean isBundle() {
		return bundle;
	}
	public void setBundle(boolean bundle) {
		this.bundle = bundle;
	}
	public List<OglasDTO> getOglasi() {
		return oglasi;
	}
	public void setOglasi(List<OglasDTO> oglasi) {
		this.oglasi = oglasi;
	}
	
	
	
}
