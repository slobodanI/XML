package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OcenaOdgovorDTO {
	
	@NotNull
	@Size(min = 1, max = 50)
	private String odgovor;

	public OcenaOdgovorDTO(String odgovor) {
		super();
		this.odgovor = odgovor;
	}
	
	public OcenaOdgovorDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getOdgovor() {
		return odgovor;
	}

	public void setOdgovor(String odgovor) {
		this.odgovor = odgovor;
	}
	
	
	
}
