package rs.xml.agent.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PorukaNewDTO {
	
	@NotNull
	@Size(min = 1, max = 50)
	private String body;
	
	public PorukaNewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
	
}
