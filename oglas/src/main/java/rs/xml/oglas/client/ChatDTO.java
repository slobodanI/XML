package rs.xml.oglas.client;

public class ChatDTO {
	
	private Long id;

	private String senderUsername;

	private String receivereUsername;
	
	private String cid;
	
	public ChatDTO() {
		// TODO Auto-generated constructor stub
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
	
}
