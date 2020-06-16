package rs.xml.agent.dto;

import rs.xml.agent.model.Model;

public class ModelDTO {
	
	private Long id;
	
	private String name;
	
	public ModelDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ModelDTO(Model model) {
			this.id = model.getId();
			this.name = model.getName();
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
