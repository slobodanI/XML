package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;

import rs.xml.agent.model.Menjac;

public class MenjacDTO {
	
	private Long id;
	
	private String name;
	
	public MenjacDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public MenjacDTO(Menjac menjac) {
		this.id = menjac.getId();
		this.name = menjac.getName();
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
