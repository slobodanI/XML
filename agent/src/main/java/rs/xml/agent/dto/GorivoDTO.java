package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;

import rs.xml.agent.model.Gorivo;

public class GorivoDTO {
	
private Long id;
	
	private String name;
	
	public GorivoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public GorivoDTO(Gorivo gorivo) {
		this.id = gorivo.getId();
		this.name = gorivo.getName();
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
