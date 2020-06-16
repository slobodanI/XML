package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;

import rs.xml.agent.model.Klasa;

public class KlasaDTO {
	
private Long id;
	
	private String name;
	
	public KlasaDTO() {
		// TODO Auto-generated constructor stub
	}
	public KlasaDTO(Klasa klasa) {
		this.id = klasa.getId();
		this.name = klasa.getName();
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
