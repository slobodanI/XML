package rs.xml.agent.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CHAT")
public class Chat {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sender")
	private Long senderId;
	
	@Column(name = "receiver")
	private Long receiverId;
	
	@OneToMany(mappedBy = "chat")
	private List<Poruka> poruke;
	
	public Chat() {
		// TODO Auto-generated constructor stub
	}

	public Chat(Long senderId, Long receiverId, List<Poruka> poruke) {
		super();
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.poruke = poruke;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public List<Poruka> getPoruke() {
		return poruke;
	}

	public void setPoruke(List<Poruka> poruke) {
		this.poruke = poruke;
	}
	
	
}
