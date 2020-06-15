package rs.xml.agent.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OcenaNewDTO {
	
	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	private int ocena;
	@NotNull
	@Size(min = 1, max = 50)
	private String komentar;
	@NotNull
	@Min(value = 0)
	private Long zahtevId;
	@NotNull
	@Min(value = 0)
	private Long oglasId;
	
	
	public OcenaNewDTO() {
		// TODO Auto-generated constructor stub
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

	public Long getZahtevId() {
		return zahtevId;
	}

	public void setZahtevId(Long zahtevId) {
		this.zahtevId = zahtevId;
	}

	public Long getOglasId() {
		return oglasId;
	}

	public void setOglasId(Long oglasId) {
		this.oglasId = oglasId;
	}
	
	
}
