package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;

public class MarkaNewDTO {
	
	@NotNull
	private String name;
	
	public MarkaNewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
