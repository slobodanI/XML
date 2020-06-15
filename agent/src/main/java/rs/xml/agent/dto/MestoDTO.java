package rs.xml.agent.dto;

import rs.xml.agent.model.Mesto;

public class MestoDTO {
	
	private Long id;
	
	private String name;
	
	public MestoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public MestoDTO(Mesto mesto) {
		this.id = mesto.getId();
		this.name=mesto.getName();
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
