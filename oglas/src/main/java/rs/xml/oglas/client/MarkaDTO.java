package rs.xml.oglas.client;

import javax.validation.constraints.NotNull;

public class MarkaDTO {
	
private Long id;
	
	private String name;
	
	public MarkaDTO() {
		// TODO Auto-generated constructor stub
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
