package rs.xml.oglas.client;

import javax.validation.constraints.NotNull;

public class ChatNewDTO {
	
	private String sendereUsername;
	
	private String receiverUsername;
	
	public ChatNewDTO() {
		
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
