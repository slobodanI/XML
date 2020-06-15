package rs.xml.agent.dto;

import java.sql.Timestamp;

import rs.xml.agent.model.Poruka;

public class PorukaDTO {

	private Timestamp timestamp;

	private String body;
	
	private String senderUsername;
	
	public PorukaDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public PorukaDTO(Poruka poruka) {
		this.timestamp = poruka.getTimestamp();
		this.body = poruka.getBody();
		this.senderUsername = poruka.getSenderUsername();
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}
	
	
	
}
