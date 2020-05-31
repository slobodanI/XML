package rs.xml.sifrarnik.dto;

import javax.validation.constraints.NotNull;

public class MenjacNewDTO {
	
	@NotNull
	private String name;
	
	public MenjacNewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
