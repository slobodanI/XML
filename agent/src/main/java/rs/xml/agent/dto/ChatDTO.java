package rs.xml.agent.dto;

import rs.xml.agent.model.Chat;

public class ChatDTO {
	
	private Long id;

	private String senderUsername;

	private String receivereUsername;
	
	public ChatDTO() {
		// TODO Auto-generated constructor stub
	}

	public ChatDTO(Chat chat) {
		this.id = chat.getId();
		this.senderUsername = chat.getSenderUsername();
		this.receivereUsername = chat.getReceiverUsername();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getReceivereUsername() {
		return receivereUsername;
	}

	public void setReceivereUsername(String receivereUsername) {
		this.receivereUsername = receivereUsername;
	}
	
	
	
}
