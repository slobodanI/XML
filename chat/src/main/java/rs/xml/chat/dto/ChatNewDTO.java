package rs.xml.chat.dto;

import javax.validation.constraints.NotNull;

public class ChatNewDTO {
	
	@NotNull
	private String sendereUsername;
	
	@NotNull
	private String receiverUsername;
	
	public ChatNewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getSendereUsername() {
		return sendereUsername;
	}

	public void setSendereUsername(String sendereUsername) {
		this.sendereUsername = sendereUsername;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}
	
	
	
}
