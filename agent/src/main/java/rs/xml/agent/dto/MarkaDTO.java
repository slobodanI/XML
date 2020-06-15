package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;

import rs.xml.agent.model.Marka;

public class MarkaDTO {
	
private Long id;
	
	private String name;
	
	public MarkaDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public MarkaDTO(Marka marka) {
		this.id = marka.getId();
		this.name = marka.getName();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
