package rs.xml.sifrarnik.dto;

import javax.validation.constraints.NotNull;

public class ModelNewDTO {
	
	@NotNull
	private String name;
	
	public ModelNewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
