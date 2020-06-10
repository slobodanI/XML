package rs.xml.chat.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import rs.xml.chat.dto.PorukaNewDTO;

@Entity
@Table(name = "PORUKA")
public class Poruka {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Chat chat;
	
	@Column(name = "timestamp")
	private Timestamp timestamp;
	
	@Column(name = "body")
	private String body;
	
	@Column(name = "sender_username")
	private String senderUsername;
	
	public Poruka() {
		// TODO Auto-generated constructor stub
	}

	public Poruka(Chat chat, Timestamp timestamp, String body, String senderUsername) {
		super();
		this.chat = chat;
		this.timestamp = timestamp;
		this.body = body;
		this.senderUsername = senderUsername;
	}

	public Poruka(PorukaNewDTO porukaNewDTO, Chat chat, String username) {		
		this.chat = chat;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.body = porukaNewDTO.getBody();
		this.senderUsername = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
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
