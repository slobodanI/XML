package rs.xml.agent.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import rs.xml.agent.dto.ChatNewDTO;

@Entity
@Table(name = "CHAT")
public class Chat {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender_username")
	private String senderUsername;
	
	@Column(name = "receiver_username")
	private String receiverUsername;
	
	@OneToMany(mappedBy = "chat")
	private List<Poruka> poruke = new ArrayList<Poruka>();
	
	public Chat() {
		// TODO Auto-generated constructor stub
	}
	
	public Chat(String senderUsername, String receiverUsername, List<Poruka> poruke) {
		this.senderUsername = senderUsername;
		this.receiverUsername = receiverUsername;
		this.poruke = poruke;
	}

	public Chat(@Valid ChatNewDTO chatDTO) {
		this.senderUsername = chatDTO.getSendereUsername();
		this.receiverUsername = chatDTO.getReceiverUsername();
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

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public List<Poruka> getPoruke() {
		return poruke;
	}

	public void setPoruke(List<Poruka> poruke) {
		this.poruke = poruke;
	}
	
	
}
